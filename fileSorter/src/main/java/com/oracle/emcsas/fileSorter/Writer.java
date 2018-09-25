package com.oracle.emcsas.fileSorter;

import com.google.common.flogger.FluentLogger;
import com.oracle.emcsas.fileSorter.LmdbStore.ReadRequest;
import com.oracle.emcsas.fileSorter.LmdbStore.WriteParam;
import com.oracle.emcsas.utils.AppMetrics;
import com.oracle.emcsas.utils.BufferSize;
import com.oracle.emcsas.utils.Functions;
import com.oracle.emcsas.utils.TimeProvider;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardOpenOption.READ;
import static java.util.concurrent.TimeUnit.MINUTES;

public final class Writer implements Runnable {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    private static final int batchSize = (int) MINUTES.toMillis(1);

    public static final LocalDateTime POISON_PILL = LocalDate.parse("1970-01-01").atStartOfDay();

    private final Path input;
    private final TimeProvider provider;
    private final int payloadSize;
    private final LmdbStore store;
    private final BlockingQueue<LocalDateTime> readQ;
    private final BlockingQueue<ReadRequest> deleteQ;

    private boolean noMoreDeletes = false;

    public Writer(Path input,
                  BufferSize size,
                  LmdbStore store,
                  BlockingQueue<LocalDateTime> queue, BlockingQueue<ReadRequest> deleteQ) {
        this.input = input;
        this.payloadSize = size.intSize();
        this.store = store;
        this.readQ = queue;
        this.deleteQ = deleteQ;
        this.provider = TimeProvider.instance();
    }

    @Override
    public void run() {
        // read a minute of data
        int len = payloadSize * batchSize;

        int batchCount = 0;
        try (ReadableByteChannel byteChannel = Files.newByteChannel(input, READ)) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(len);
            while (byteChannel.read(buffer) != -1) {
                batchCount++;
                buffer.flip();

                writeToFileStore(buffer);

                // signal to reader every 10 mins
                if (batchCount % 10 == 0) {
                    long timestamp = buffer.getLong(Integer.BYTES);
                    OffsetDateTime instant = Instant.ofEpochMilli(timestamp).atOffset(ZoneOffset.UTC);
                    readQ.add(instant.toLocalDateTime());
                }

                buffer.clear();

                deleteIfAny();
            }
        } catch (IOException e) {
            logger.atSevere().withCause(e).log("Error while opening input file: %s", input);
        }

        if (noMoreDeletes) deleteRemaining();
    }

    private void deleteRemaining() {
        // delete remaining requests
        ReadRequest r;
        try {
            while ((r = deleteQ.take()) != Reader.POISON_PILL) {
                delete(r);
            }
        } catch (InterruptedException e) {
            logger.atSevere().withCause(e).log("Exception while removing head from delete request queue");
        }
    }

    private void delete(ReadRequest r) {
        long t0 = System.nanoTime();
        store.deleteInRange(r);
        long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t0);
        AppMetrics.newHistogram("kv-store:delete").update(millis);
    }

    private void deleteIfAny() {
        if (noMoreDeletes) return;

        ReadRequest r = deleteQ.poll();
        if (r == null) return;
        if (r == Reader.POISON_PILL) {
            noMoreDeletes = true;
            return;
        }

        delete(r);
    }

    private void writeToFileStore(ByteBuffer buffer) {
        final int end = buffer.limit();
        List<WriteParam<ByteBuffer>> params = new ArrayList<>(batchSize);
        for (int start = 0; start != end; start = buffer.limit()) {
            int delta = Math.min(end - start, payloadSize); // payloadSize == stride
            buffer.position(start).limit(start + delta);

            ByteBuffer view = buffer.slice();

            long timestamp = buffer.getLong(Integer.BYTES);

            provider.updateCurrentTimeMillis(timestamp);

            String tenant = Functions.getString(buffer);
            WriteParam<ByteBuffer> param = new WriteParam<>(tenant, timestamp, view);
            params.add(param);
        }

        long t0 = System.nanoTime();
        store.bulkAdd(params);
        long nanos = System.nanoTime() - t0;
        long millis = TimeUnit.NANOSECONDS.toMillis(nanos);
        AppMetrics.registry.histogram("kv-store:write").update(millis);
    }

    //<editor-fold desc="ByteBuffer test">
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 10);
        for (int i = 0; i < 10; i++) buffer.putInt(i);

        buffer.flip();

        // read in groups of 3
        // so 4 BBs, 3 of size 3 and 1 of 1
        final int end = buffer.limit(), stride = 3 * Integer.BYTES;

        for (int start = 0; start != end; start = buffer.limit()) {
            int delta = Math.min(end - start, stride);
            buffer.position(start).limit(start + delta);
            _consume(buffer.asReadOnlyBuffer());
        }
    }

    private static void _consume(ByteBuffer buffer) {
        System.out.println(Functions.bb(buffer));
        while (buffer.hasRemaining()) System.out.print(buffer.getInt() + "  ");
        System.out.println();
    }
    //</editor-fold>
}
