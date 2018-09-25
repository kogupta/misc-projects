package org.kogupta.diskStore.lmdbStore;

import com.google.common.flogger.FluentLogger;
import com.jakewharton.byteunits.BinaryByteUnit;
import org.lmdbjava.*;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.Function;

import static java.nio.ByteOrder.nativeOrder;
import static org.lmdbjava.DbiFlags.*;
import static org.lmdbjava.EnvFlags.MDB_MAPASYNC;
import static org.lmdbjava.EnvFlags.MDB_NOSYNC;
import static org.lmdbjava.EnvFlags.MDB_WRITEMAP;

public final class LmdbStore {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    private static final Comparator<ByteBuffer> cmp = Comparator.comparing(a -> a.order(nativeOrder()));

    private final Env<ByteBuffer> env;
    private final Map<String, Dbi<ByteBuffer>> databases;

    private final ByteBuffer key;

    LmdbStore(File dir, String[] tenants) {
        env = Env.create()
                .setMaxDbs(10) // TODO
                .setMapSize(BinaryByteUnit.GIBIBYTES.toBytes(1))
                .setMaxReaders(4)
                .open(dir, MDB_WRITEMAP, MDB_NOSYNC, MDB_MAPASYNC);
        databases = new HashMap<>();
        key = ByteBuffer.allocateDirect(8).order(nativeOrder());

        // pre-create dbi
        for (String tenant : tenants) {
            // TODO
//            Dbi<ByteBuffer> dbi = env.openDbi(tenant, MDB_CREATE, MDB_INTEGERKEY, MDB_DUPSORT);
            Dbi<ByteBuffer> dbi = env.openDbi(tenant, MDB_CREATE, MDB_INTEGERKEY);
            databases.put(tenant, dbi);
        }
    }

    public void bulkAdd(List<WriteParam<ByteBuffer>> params) {
        try (Txn<ByteBuffer> txn = env.txnWrite()) {
            for (WriteParam<ByteBuffer> param : params) {
                key.clear();
                key.putLong(param.timestamp).flip();

                Dbi<ByteBuffer> dbi = getOrCreateDbi(param.tenant);
                dbi.put(txn, key, param.payload);
            }
            txn.commit();
            logger.atInfo().log("Adding %d kv pairs to store", params.size());
        }
    }

    private Dbi<ByteBuffer> getOrCreateDbi(String tenantId) {
        if (!databases.containsKey(tenantId)) {
            throw new IllegalArgumentException("Unsupported tenant: " + tenantId);
        }

        return databases.get(tenantId);
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

        Dbi<ByteBuffer> dbi = getOrCreateDbi(req.tenant);
        try (Txn<ByteBuffer> txnRead = env.txnRead();
             CursorIterator<ByteBuffer> itr = dbi.iterate(txnRead, range, cmp)) {
            for (CursorIterator.KeyVal<ByteBuffer> ignore : itr.iterable()) {
                count++;
            }
        }

//        try (Txn<ByteBuffer> txnRead = env.txnRead();
//             CursorIterator<ByteBuffer> curItr = dbi.iterate(txnRead, KeyRange.all())) {
//            for (CursorIterator.KeyVal<ByteBuffer> ignore : curItr.iterable()) {
//                count++;
//            }
//        }

//        try (Txn<ByteBuffer> txnRead = env.txnRead();
//             Cursor<ByteBuffer> c = dbi.openCursor(txnRead)) {
//            boolean b = c.get(range.getStart(), GetOp.MDB_SET_RANGE);
//            c.seek()
//        }







        return count;
    }

    public void close() {
        env.close();
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
