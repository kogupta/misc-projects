package org.kogupta.diskStore.datagen;

import com.google.common.flogger.FluentLogger;
import org.kogupta.diskStore.Pojo;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.nio.file.StandardOpenOption.*;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.kogupta.diskStore.utils.Functions.*;

public final class DataGenerator {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    private static final int aMin = (int) MINUTES.toMillis(1);
    private static final int _5Min = aMin * 5;

    private static ByteBuffer buffer;
    private static int id = 0;

    public static void main(String[] _args) throws IOException {
        Args args = Args.parse(_args);

        generateUsing(args);
    }

    public static void generateUsing(Args args) {
        long start = toMillis(args.fromDate);
        long end = toMillis(args.endDate);

        Path target = Paths.get(args.dataDir.toString(), "data.bin");

        int intervalCount = (int) ((end - start) / _5Min);
        logger.atInfo().log("# of 5 minute intervals: %d", intervalCount);

        buffer = createBB(args.bufferSize.intSize());

        for (long ts = start; ts < end; ts += _5Min) {
            for (String tenant : tenants(args.tenantCount)) {
                waitAMinute(tenant, target, args, ts);
            }
        }

        logger.atInfo().log("Total # of events written: %,d", id);

        logger.atInfo().log("Total size of file [%s] written: %,d",
                            target.toAbsolutePath(),
                            target.toFile().length());
    }

    // wait 5 mins actually
    private static void waitAMinute(String tenant, Path target, Args args, long start) {
        int bufSize = args.bufferSize.intSize();

        long end = start + _5Min;

        buffer.clear();
        int n = 0;
        for (long ts = start; ts < end; ts++, n += bufSize) {
            Pojo pojo = new Pojo();
            pojo.setId(id++);
            pojo.setTenantId(tenant);
            pojo.setTimestamp(ts);
            LocalDateTime _payload = args.fromDate.plus(ts - start, ChronoUnit.MILLIS);
            pojo.setPayload(_payload.toString());

            writeToBB(pojo, buffer);
            buffer.position(n);
//            logger.atInfo().every(10_000).log("updating bb states: %s", bb(buffer));
        }

        // 0 .... 1 .... 2 .... 3 .... 4 .... 5
        //       -x-   -2x-     ...
        //   delay this: ^ .... ^

        // write from (0, 2 min)
        buffer.position(0).limit(aMin * bufSize * 2);
        write(target, buffer);

        // write (3, 5)
        buffer.limit(_5Min * bufSize).position(aMin * bufSize * 3);
        write(target, buffer);

        // write (2, 3)
        buffer.position(aMin * bufSize * 2).limit(aMin * bufSize * 3);
        write(target, buffer);
    }

    private static ByteBuffer createBB(int bufSize) {
        int len = _5Min * bufSize;
        logger.atInfo().log("Length of 5 minute buffer: %,d", len);
        ByteBuffer buffer = ByteBuffer.allocateDirect(len);
        logger.atInfo().log("Created ByteBuffer: %s", bb(buffer));
        return buffer;
    }

    private static void write(Path target, ByteBuffer buffer) {
        logger.atInfo().log("Writing to file: %s", bb(buffer));

        try (SeekableByteChannel channel = Files.newByteChannel(target, CREATE, WRITE, APPEND)) {
            int n = channel.write(buffer);
            logger.atInfo().log("# of bytes written: %,d", n);
        } catch (IOException e) {
            logger.atSevere().withCause(e).log("Error while writing to target binary file");
            throw new UncheckedIOException(e);
        }
    }
}
