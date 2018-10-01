package org.kogupta.diskStore.lmdbStore;

import com.google.common.io.MoreFiles;
import com.jakewharton.byteunits.BinaryByteUnit;
import org.lmdbjava.*;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

import static java.nio.ByteBuffer.allocateDirect;
import static java.nio.ByteBuffer.wrap;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.lmdbjava.DbiFlags.MDB_CREATE;
import static org.lmdbjava.DbiFlags.MDB_DUPSORT;
import static org.testng.Assert.*;

@Test
public class PrefixMatchTest {
    private static final Path path = Paths.get("/tmp/fileSorter/lmdb-test");

    private Env<ByteBuffer> env;

    @BeforeTest
    public void createStore() throws IOException {
        Path _path = Files.createDirectories(path);
        assertNotNull(_path);
        assertTrue(Files.exists(_path));
        assertTrue(Files.isDirectory(_path));
        env = Env.<ByteBuffer>create()
                .setMaxDbs(1)
                .setMapSize(BinaryByteUnit.MEBIBYTES.toBytes(10))
                .open(_path.toFile());
    }

    @Test
    public void iterator() {
        Dbi<ByteBuffer> dbi = env.openDbi("prefix-match", MDB_CREATE, MDB_DUPSORT);

        byte[] host = "host".getBytes(UTF_8);

        // 1host => a, b, c
        // 1db   => p, q, r
        // 2host => 2a, 2b, 2c
        // 3db   => 3p, 3q, 3r
        ByteBuffer key = key(1, "host");
        put(dbi, key, "a", "b", "c");

        key.putInt(2).put(host).flip();
        put(dbi, key, "2a", "2b", "2c");

        KeyRange<ByteBuffer> range = KeyRange.atLeast(key);
        try (Txn<ByteBuffer> txn = env.txnRead();
             CursorIterator<ByteBuffer> itr = dbi.iterate(txn, range)) {
            int count = 0;
            for (CursorIterator.KeyVal<ByteBuffer> kv : itr.iterable()) {
                ByteBuffer k = kv.key();
                oneOf(k.getInt(), 1, 2);
                assertEquals(UTF_8.decode(k).toString(), "host");

                oneOf(UTF_8.decode(kv.val()).toString(), "2a", "2b", "2c");

                count++;
            }

            assertEquals(count, 3);
        }

        ByteBuffer from = key(1, "host");
        ByteBuffer to = key(2, "host");

        try (Txn<ByteBuffer> txn = env.txnRead();
             CursorIterator<ByteBuffer> itr = dbi.iterate(txn, KeyRange.closed(from, to))) {
            int count = 0;
            for (CursorIterator.KeyVal<ByteBuffer> kv : itr.iterable()) {
                int k = kv.key().getInt();
                oneOf(k, 1, 2);
                assertEquals(UTF_8.decode(kv.key()).toString(), "host");

                if (k == 1) oneOf(UTF_8.decode(kv.val()).toString(), "a", "b", "c");
                if (k == 2) oneOf(UTF_8.decode(kv.val()).toString(), "2a", "2b", "2c");

                count++;
            }

            assertEquals(count, 6);
        }
    }

    @Test
    public void prefixMatch2() {
        Dbi<ByteBuffer> dbi = env.openDbi("prefix-match", MDB_CREATE, MDB_DUPSORT);

        byte[] host = "host".getBytes(UTF_8);
        byte[] db = "db".getBytes(UTF_8);

        // 1host => a, b, c
        // 1db   => p, q, r
        // 2host => 2a, 2b, 2c
        // 3db   => 3p, 3q, 3r
        put(dbi, key(1, "host"), "a", "b", "c");
        put(dbi, key(1, "db"), "p", "q", "r");
        put(dbi, key(2, "host"), "2a", "2b", "2c");
        put(dbi, key(3, "db"), "2a", "2b", "2c");
        put(dbi, key(5, "host"), "5a", "5b", "5c");

        // find all "host"

        ByteBuffer from = key(1, "host");
        ByteBuffer to = key(5, "host");
        KeyRange<ByteBuffer> range = KeyRange.closed(from, to);

        try (Txn<ByteBuffer> txn = env.txnRead();
             CursorIterator<ByteBuffer> itr = dbi.iterate(txn, range)) {
            ByteBuffer _host = wrap(host);

            int[] expectedIntKeys = {1, 2, 5};
            int count = 0;

            for (CursorIterator.KeyVal<ByteBuffer> kv : itr.iterable()) {
                ByteBuffer key = kv.key();
                key.position(4);

                if (key.compareTo(_host) != 0) continue;

                assertEquals(UTF_8.decode(key).toString(), "host");
                assertTrue(contains(expectedIntKeys, key.getInt(0)));
                count++;
            }

            assertEquals(count, expectedIntKeys.length * 3);
        }
    }

    private static ByteBuffer bb(int n) {
        ByteBuffer buffer = allocateDirect(4);
        buffer.putInt(n).flip();
        return buffer;
    }

    private static ByteBuffer key(int n, String s) {
        ByteBuffer bb = allocateDirect(12);
        bb.putInt(n).put(s.getBytes(UTF_8)).flip();
        return bb;
    }

    private static boolean contains(int[] expected, int actual) {
        for (int n : expected) if (n == actual) return true;

        return false;
    }

    private static void oneOf(int actual, int... expected) {
        for (int n : expected) if (actual == n) return;

        String s = "Expected one of: " + Arrays.toString(expected) + " but found: " + actual;
        throw new AssertionError(s);
    }

    private static void oneOf(String actual, String... expected) {
        for (String s : expected) if (Objects.equals(actual, s)) return;

        String s = "Expected one of: " + Arrays.toString(expected) + " but found: " + actual;
        throw new AssertionError(s);
    }

    private void put(Dbi<ByteBuffer> dbi, ByteBuffer key, String... vals) {
        ByteBuffer val = allocateDirect(4);
        for (String s : vals) {
            val.put(s.getBytes(UTF_8)).flip();
            dbi.put(key, val);
            val.clear();
        }
    }

    @AfterTest
    public void close() throws IOException {
        env.close();
        MoreFiles.deleteRecursively(path);
        assertFalse(path.toFile().exists());
    }

}
