package org.kogupta.diskStore.lmdbStore;

import com.google.common.flogger.FluentLogger;
import com.google.common.io.MoreFiles;
import org.kogupta.diskStore.Pojo;
import org.kogupta.diskStore.lmdbStore.LmdbStore.ReadRequest;
import org.kogupta.diskStore.lmdbStore.LmdbStore.WriteParam;
import org.kogupta.diskStore.utils.BufferSize;
import org.kogupta.diskStore.utils.Functions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardOpenOption.READ;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.kogupta.diskStore.utils.Functions.getString;
import static org.kogupta.diskStore.utils.Functions.toMillis;
import static org.testng.Assert.*;

public class LmdbStoreTest {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    private static final String[] tenants = Functions.tenants(10);
    private static final Path path = Paths.get("/tmp/fileSorter/lmdb-test");
    private static final int batchSize = (int) SECONDS.toMillis(1);
    private static final int payload = BufferSize.OneK.intSize();

    private LmdbStore store;

    @BeforeTest
    public void createStore() throws IOException {
        Path _path = Files.createDirectories(path);
        assertNotNull(_path);
        File f = path.toFile();
        assertTrue(f.exists());
        assertTrue(f.isDirectory());
        store = new LmdbStore(f, tenants);
    }

    @Test
    public void testBulkAdd() {
        List<WriteParam<ByteBuffer>> params = new ArrayList<>();
        LocalDateTime _start = LocalDate.of(2018, Month.JANUARY, 1).atStartOfDay();
        long start = toMillis(_start);
        for (String tenant : tenants) {
            ByteBuffer payload = ByteBuffer.allocateDirect(8);

            params.add(new WriteParam<>(tenant, start, payload));
        }
        store.bulkAdd(params);

        for (String tenant : tenants) {
            ReadRequest req = new ReadRequest(tenant, start, start + 10);
            int n = store.countKeysInRange(req);
            assertEquals(n, 1);
        }
    }

    @Test
    public void testBulkAdd2() throws IOException {
        Path input = Paths.get("/tmp/fileSorter/data.bin");
        assertTrue(input.toFile().exists());
        assertTrue(input.toFile().isFile());

        int len = payload * batchSize;
        ByteBuffer buffer = ByteBuffer.allocateDirect(len);

        try (ReadableByteChannel byteChannel = Files.newByteChannel(input, READ)) {
            int read = byteChannel.read(buffer);
            assertTrue(read != -1);
            buffer.flip();
            HashMap<String, MinMax> acc = new HashMap<>();
            writeToFileStore(buffer, acc);
            for (Map.Entry<String, MinMax> entry : acc.entrySet()) {
                String tenant = entry.getKey();
                long min = entry.getValue().min;
                long max = entry.getValue().max;
                ReadRequest req = new ReadRequest(tenant, min, max + 1);
                int n = store.countKeysInRange(req);
                assertTrue(n > 0);
                assertEquals(n, batchSize);

                List<Pojo> xs = store.read(req, Functions::fromByteBuffer);
                assertNotNull(xs);
                assertFalse(xs.isEmpty());
                assertEquals(xs.size(), batchSize);
            }
        }
    }

    private void writeToFileStore(ByteBuffer buffer, Map<String, MinMax> acc) {
        int end = buffer.limit();
        List<WriteParam<ByteBuffer>> params = new ArrayList<>(batchSize);
        for (int start = 0; start != end; start = buffer.limit()) {
            int delta = Math.min(end - start, payload); // payloadSize == stride
            buffer.position(start).limit(start + delta);

            ByteBuffer view = buffer.slice();

            // byteBuffer position madness!
            buffer.position(buffer.position() + Integer.BYTES);
            long timestamp = buffer.getLong();
            String tenant = getString(buffer);

            acc.compute(tenant, (s, minMax) ->
                    minMax == null ? new MinMax().add(timestamp) : minMax.add(timestamp));

            WriteParam<ByteBuffer> param = new WriteParam<>(tenant, timestamp, view);
            params.add(param);
        }

        store.bulkAdd(params);
    }

    @AfterTest
    public void close() throws IOException {
        store.close();
        MoreFiles.deleteRecursively(path);
        assertFalse(path.toFile().exists());
    }

    private static final class MinMax {
        long min = Long.MAX_VALUE;
        long max = 0;

        MinMax add(long n) {
            min = Math.min(min, n);
            max = Math.max(max, n);
            return this;
        }

        @Override
        public String toString() {
            return String.format("min: %d, max: %d", min, max);
        }
    }
}