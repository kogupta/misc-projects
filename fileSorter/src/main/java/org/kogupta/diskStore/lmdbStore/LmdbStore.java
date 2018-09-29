package org.kogupta.diskStore.lmdbStore;

import com.google.common.flogger.FluentLogger;
import com.jakewharton.byteunits.BinaryByteUnit;
import org.lmdbjava.*;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.function.Function;

import static java.nio.ByteOrder.nativeOrder;
import static org.kogupta.diskStore.utils.Functions.fromMillis;
import static org.lmdbjava.DbiFlags.MDB_CREATE;
import static org.lmdbjava.DbiFlags.MDB_INTEGERKEY;
import static org.lmdbjava.EnvFlags.*;

public final class LmdbStore {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    private static final Comparator<ByteBuffer> cmp = Comparator.comparing(a -> a.order(nativeOrder()));

    private final Env<ByteBuffer> env;
    private final Map<String, Dbi<ByteBuffer>> databases;

    private final ByteBuffer key;

    LmdbStore(File dir, String[] tenants) {
        env = Env.create()
                .setMaxDbs(10) // TODO
                .setMapSize(BinaryByteUnit.GIBIBYTES.toBytes(20))
                .setMaxReaders(4)
                .open(dir, MDB_WRITEMAP, MDB_NOSYNC, MDB_MAPASYNC);
        databases = new HashMap<>();
        key = ByteBuffer.allocateDirect(8).order(nativeOrder());

        // max VALUE size when MDB_DUPSORT is used
        logger.atInfo().log("Env max key size: %d", env.getMaxKeySize());

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
        Dbi<ByteBuffer> dbi = getOrCreateDbi(req.tenant);

        logger.atInfo().log("Start: %s, end: %s",
                            fromMillis(req.fromInclusive),
                            fromMillis(req.to));

        List<T> xs = new ArrayList<>();
        ByteBuffer from = bbTime(req.fromInclusive);

        try (Txn<ByteBuffer> txnRead = env.txnRead();
             Cursor<ByteBuffer> c = dbi.openCursor(txnRead)) {
            if (!c.get(from, GetOp.MDB_SET_RANGE)) {
                return Collections.emptyList();
            }

            long cur = c.key().order(nativeOrder()).getLong();
            while (Long.compare(cur, req.to) < 0) {
                xs.add(converter.apply(c.val()));
                if (!c.next()) return xs;
                cur = c.key().order(nativeOrder()).getLong();
            }
        }

        return xs;
    }

    public int countKeysInRange(ReadRequest req) {
        Dbi<ByteBuffer> dbi = getOrCreateDbi(req.tenant);

        ByteBuffer from = bbTime(req.fromInclusive);
        try (Txn<ByteBuffer> txnRead = env.txnRead();
             Cursor<ByteBuffer> c = dbi.openCursor(txnRead)) {
            if (!c.get(from, GetOp.MDB_SET_RANGE)) {
                return 0;
            }

            long cur = c.key().order(nativeOrder()).getLong();
            int n = 0;
            while (Long.compare(cur, req.to) < 0) {
                n++;
                if (!c.next()) return n;
                cur = c.key().order(nativeOrder()).getLong();
            }

            return n;
        }

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

}
