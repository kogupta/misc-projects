package org.kogupta.diskStore.lmdbStore;

import com.google.common.flogger.FluentLogger;
import org.kogupta.diskStore.utils.AppMetrics;
import org.kogupta.diskStore.utils.BufferSize;
import org.kogupta.diskStore.utils.Functions;
import org.kogupta.diskStore.utils.TimeProvider;

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
    public static final LocalDateTime POISON_PILL = LocalDate.parse("1970-01-01").atStartOfDay();
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    private static final int batchSize = (int) MINUTES.toMillis(1);
    private final Path input;
    private final int payloadSize;
    private final LmdbStore store;
    private final BlockingQueue<LocalDateTime> readQ;
    private final BlockingQueue<LmdbStore.ReadRequest> deleteQ;

    private boolean noMoreDeletes = false;

    public Writer(Path input,
                  BufferSize size,
                  LmdbStore store,
                  BlockingQueue<LocalDateTime> queue, BlockingQueue<LmdbStore.ReadRequest> deleteQ) {
        this.input = input;
        this.payloadSize = size.intSize();
        this.store = store;
        this.readQ = queue;
        this.deleteQ = deleteQ;
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

    private void writeToFileStore(ByteBuffer buffer) {
        final int end = buffer.limit();
        List<LmdbStore.WriteParam<ByteBuffer>> params = new ArrayList<>(batchSize);
        for (int start = 0; start != end; start = buffer.limit()) {
            int delta = Math.min(end - start, payloadSize); // payloadSize == stride
            buffer.position(start).limit(start + delta);

            ByteBuffer view = buffer.slice();
            logger.atInfo().log(Functions.bb(view));

            long timestamp = buffer.getLong(Integer.BYTES);

            String tenant = Functions.getString(buffer);
            LmdbStore.WriteParam<ByteBuffer> param = new LmdbStore.WriteParam<>(tenant, timestamp, view);
            params.add(param);
        }

        long t0 = System.nanoTime();
        store.bulkAdd(params);
        long nanos = System.nanoTime() - t0;
        long millis = TimeUnit.NANOSECONDS.toMillis(nanos);
        AppMetrics.registry.histogram("kv-store:write").update(millis);
    }

    private void deleteRemaining() {
        // delete remaining requests
        LmdbStore.ReadRequest r;
        try {
            while ((r = deleteQ.take()) != Reader.POISON_PILL) {
                delete(r);
            }
        } catch (InterruptedException e) {
            logger.atSevere().withCause(e).log("Exception while removing head from delete request queue");
        }
    }

    private void delete(LmdbStore.ReadRequest r) {
        long t0 = System.nanoTime();
        store.deleteInRange(r);
        long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t0);
        AppMetrics.newHistogram("kv-store:delete").update(millis);
    }

    private void deleteIfAny() {
        if (noMoreDeletes) return;

        LmdbStore.ReadRequest r = deleteQ.poll();
        if (r == null) return;
        if (r == Reader.POISON_PILL) {
            noMoreDeletes = true;
            return;
        }

        delete(r);
    }

    //<editor-fold desc="ByteBuffer test">
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES * 10);
        for (int i = 0; i < 10; i++) buffer.putInt(i);

        buffer.flip();

        // read in groups of 3
        // so 4 BBs, 3 of size 3 and 1 of 1
        final int end = buffer.limit(), stride = 2 * Integer.BYTES;

        for (int start = 0; start != end; start = buffer.limit()) {
            int delta = Math.min(end - start, stride);
            buffer.position(start).limit(start + delta);
            _consume(buffer.slice());
        }
    }

    private static void _consume(ByteBuffer buffer) {
        System.out.println(Functions.bb(buffer));
        while (buffer.hasRemaining()) System.out.print(buffer.getInt() + "  ");
        System.out.println();
    }
    //</editor-fold>
}
