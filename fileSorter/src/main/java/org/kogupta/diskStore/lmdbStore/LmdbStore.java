package org.kogupta.diskStore.lmdbStore;

import org.lmdbjava.*;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.Function;

import static java.nio.ByteOrder.nativeOrder;
import static org.lmdbjava.DbiFlags.*;

public final class LmdbStore {
    private static final Comparator<ByteBuffer> cmp = Comparator.comparing(a -> a.order(nativeOrder()));
    private static final long hundredMB = 100 * 1024 * 1024;

    private final Env<ByteBuffer> env;
    private final Map<String, Dbi<ByteBuffer>> databases;

    private final ByteBuffer key;

    public LmdbStore(File dir, int tenantCount) {
        env = Env.create()
                .setMaxDbs(tenantCount)
                .setMapSize(hundredMB)
                .setMaxReaders(4)
                .open(dir, EnvFlags.MDB_WRITEMAP);
        databases = new HashMap<>();
        key = ByteBuffer.allocateDirect(8).order(nativeOrder());
    }

    private static KeyRange<ByteBuffer> createRange(long fromInclusive, long to) {
        ByteBuffer start = bbTime(fromInclusive);
        ByteBuffer stop = bbTime(to);
        return KeyRange.closedOpen(start, stop);
    }

    private static ByteBuffer bbTime(long n) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(Long.BYTES).order(nativeOrder());
        buffer.putLong(n).flip();
        return buffer;
    }

    public void bulkAdd(List<WriteParam<ByteBuffer>> params) {
        try (Txn<ByteBuffer> txn = env.txnWrite()) {
            for (WriteParam<ByteBuffer> param : params) {
                key.clear();
                key.putLong(param.timestamp).flip();

                getOrCreateDbi(param.tenant).put(txn, key, param.payload);
            }
            txn.commit();
        }
    }

    private Dbi<ByteBuffer> getOrCreateDbi(String tenantId) {
        return databases.computeIfAbsent(tenantId,
                                         s -> env.openDbi(s, MDB_CREATE, MDB_INTEGERKEY, MDB_DUPSORT));
    }

    public int deleteInRange(ReadRequest req) {
        KeyRange<ByteBuffer> range = createRange(req.fromInclusive, req.to);

        int count = 0;

        try (Txn<ByteBuffer> txnWrite = env.txnWrite();
             CursorIterator<ByteBuffer> itr = getOrCreateDbi(req.tenant).iterate(txnWrite, range, cmp)) {
            while (itr.hasNext()) {
                CursorIterator.KeyVal<ByteBuffer> ignore = itr.next();
                itr.remove();
                count++;
            }
            txnWrite.commit();
        }

        return count;
    }

    public <T> List<T> read(ReadRequest req, Function<ByteBuffer, T> converter) {
        KeyRange<ByteBuffer> range = createRange(req.fromInclusive, req.to);

        List<T> xs = new ArrayList<>();
        try (Txn<ByteBuffer> txnRead = env.txnRead();
             CursorIterator<ByteBuffer> itr = getOrCreateDbi(req.tenant).iterate(txnRead, range, cmp)) {
            while (itr.hasNext()) {
                CursorIterator.KeyVal<ByteBuffer> kv = itr.next();
                ByteBuffer buffer = kv.val();
                xs.add(converter.apply(buffer));
            }
        }
        return xs;
    }

    public int countKeysInRange(ReadRequest req) {
        KeyRange<ByteBuffer> range = createRange(req.fromInclusive, req.to);
        int count = 0;

        try (Txn<ByteBuffer> txnRead = env.txnRead();
             CursorIterator<ByteBuffer> itr = getOrCreateDbi(req.tenant).iterate(txnRead, range, cmp)) {
            for (CursorIterator.KeyVal<ByteBuffer> ignore : itr.iterable()) {
                count++;
            }
        }

        return count;
    }

    public static final class WriteParam<T> {
        final String tenant;
        final long timestamp;
        final T payload;

        public WriteParam(String tenant, long timestamp, T payload) {
            this.tenant = tenant;
            this.timestamp = timestamp;
            this.payload = payload;
        }
    }

    public static final class ReadRequest {
        final String tenant;
        final long fromInclusive;
        final long to;

        public ReadRequest(String tenant, long fromInclusive, long to) {
            assert fromInclusive < to;

            this.tenant = tenant;
            this.fromInclusive = fromInclusive;
            this.to = to;
        }
    }
}
