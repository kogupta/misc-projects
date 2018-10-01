package org.kogupta.diskStore.lmdbStore.v2;

import com.google.common.flogger.FluentLogger;
import org.kogupta.diskStore.Pojo2;
import org.kogupta.diskStore.utils.Bucket;
import org.kogupta.diskStore.utils.Consumer3;
import org.kogupta.diskStore.utils.FileUtils;
import org.lmdbjava.*;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.jakewharton.byteunits.BinaryByteUnit.GIBIBYTES;
import static java.nio.ByteBuffer.allocateDirect;
import static java.nio.ByteOrder.nativeOrder;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.lmdbjava.DbiFlags.*;
import static org.lmdbjava.EnvFlags.MDB_NOSYNC;
import static org.lmdbjava.EnvFlags.MDB_WRITEMAP;
import static org.lmdbjava.KeyRange.closedOpen;

public final class PartitionedTenantStore {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    private static final Comparator<ByteBuffer> cmp = Comparator.comparing(a -> a.order(nativeOrder()));

    private final Env<ByteBuffer> env;
    private final Dbi<ByteBuffer>[] dataDbis; // hourly partition
    private final Dbi<ByteBuffer>[] indices;  // hourly partition
    private final ByteBuffer uuid;
    private final ByteBuffer value;
    private final ByteBuffer indexKey;

    PartitionedTenantStore(Path parentDir, String tenant, String date) {
        this.dataDbis = new Dbi[24];
        this.indices = new Dbi[24];
        Path storeDir = parentDir.resolve(tenant).resolve(date);
        this.env = getOrCreate(storeDir, tenant, date);
        uuid = allocateDirect(64);
        value = allocateDirect(4 * 1024);
        indexKey = allocateDirect(64);
    }

    public void bulkAdd(List<Pojo2> xs) {
        try (Txn<ByteBuffer> txn = env.txnWrite()) {
            for (Pojo2 x : xs) {
                Bucket bucket = Bucket.create(x.getTimestamp());
                Dbi<ByteBuffer> data = dataDbis[bucket.hour];
                Dbi<ByteBuffer> index = indices[bucket.hour];

                // write to data store
                uuid.clear();
                uuid.put(x.getUuid().getBytes(UTF_8)).flip();
                assertBB(uuid);
                value.clear();
                x.writeToBB(value).flip();
                assertBB(value);
                data.put(txn, uuid, value);

                // update index
                indexKey.clear();
                indexKey.putLong(x.getTimestamp()).put(x.getSecondaryKey().getBytes(UTF_8));
                indexKey.flip();
                assertBB(indexKey);
                index.put(txn, indexKey, uuid);
            }

            txn.commit();
        }

        logger.atInfo().log("Added %,d kv pairs to env store", xs.size());
    }

    public int countKeys(long from, long to, String secondaryKey) {
        AtomicInteger n = new AtomicInteger();
        Consumer3<Txn<ByteBuffer>, ByteBuffer, Dbi<ByteBuffer>> consumer =
                (txn, bb, dbi) -> n.incrementAndGet();

        _readQuery(from, to, secondaryKey, consumer);

        return n.get();
    }

    public List<Pojo2> read(long from, long to, String secondaryKey) {
        List<Pojo2> result = new ArrayList<>();
        Consumer3<Txn<ByteBuffer>, ByteBuffer, Dbi<ByteBuffer>> consumer = (txn, bb, dbi) -> {
            bb.position(0).limit(8);
            ByteBuffer bb2 = dbi.get(txn, bb.slice());
            Pojo2 pojo2 = Pojo2.readFromBB(bb2);
            result.add(pojo2);
        };

        _readQuery(from, to, secondaryKey, consumer);

        return result;
    }

    private void _readQuery(long from,
                            long to,
                            String secondaryKey,
                            Consumer3<Txn<ByteBuffer>, ByteBuffer, Dbi<ByteBuffer>> consumer) {
        assert from < to;

        int fromIdx = Bucket.create(from).hour;
        int toIdx = Bucket.create(to).hour;

        byte[] _bytes = secondaryKey.getBytes(UTF_8);
        ByteBuffer a = lookupKey(from, _bytes);
        ByteBuffer b = lookupKey(to, _bytes);
        KeyRange<ByteBuffer> range = closedOpen(a, b);

        for (int i = fromIdx; i <= toIdx; i++) {
            Dbi<ByteBuffer> dataDbi = dataDbis[i];
            _read2(secondaryKey, range, dataDbi, indices[i], consumer);
        }
    }

    private void _read2(String secondaryKey,
                        KeyRange<ByteBuffer> range,
                        Dbi<ByteBuffer> dataDbi,
                        Dbi<ByteBuffer> index,
                        Consumer3<Txn<ByteBuffer>, ByteBuffer, Dbi<ByteBuffer>> consumer) {
        try (Txn<ByteBuffer> txn = env.txnRead();
             CursorIterator<ByteBuffer> idxItr = index.iterate(txn, range)) {
            for (CursorIterator.KeyVal<ByteBuffer> kv : idxItr.iterable()) {
                ByteBuffer key = kv.key();
                key.position(8);
                String _secKey = UTF_8.decode(key).toString();
                if (Objects.equals(_secKey, secondaryKey)) {
                    consumer.accept(txn, key, dataDbi);
                }
            }
        }
    }

    private ByteBuffer lookupKey(long from, byte[] _bytes) {
        ByteBuffer bb = allocateDirect(64).putLong(from).put(_bytes);
        bb.flip();
        assertBB(bb);
        return bb;
    }

    /**
     * Delete the data for the given hour.
     *
     * @param hour hour of the day - value between 0 and 23
     */
    public void dropDb(int hour) {
        // ensure single threaded access
        assert hour >= 0 && hour < 24;

        if (dataDbis[hour] == null && indices[hour] == null) {
            // nothing to do here - already deleted
            logger.atInfo().log("Databases already dropped for hour: %d", hour);
            return;
        }

        if (dataDbis[hour] == null || indices[hour] == null) {
            String db = dataDbis[hour] == null ? "data dbi is null" : "index db is null";
            String s = String.format(
                    "For hour: %d, %s and other is not - either both should be null or both non-null!",
                    hour, db);
            throw new IllegalStateException(s);
        }

        try (Txn<ByteBuffer> txn = env.txnWrite()) {
            dataDbis[hour].drop(txn);
            indices[hour].drop(txn);

            // null out references to avoid reuse
            dataDbis[hour] = null;
            indices[hour] = null;

            txn.commit();
        }


        logger.atInfo().log("Dropped database for hour: %d", hour);
    }

    private static void assertBB(ByteBuffer bb) {
        // incorrect bb position/limit is cause of LOT of heartburn
        // 0 len bb => BadValueSize exception, etc
        assert bb.position() == 0 && bb.limit() > 0;
    }

    private Env<ByteBuffer> getOrCreate(Path p, String tenant, String date) {
        try {
            FileUtils.mkdir(p);
            logger.atInfo().log("Created env dir for tenant: %s and date: %s at: %s",
                                tenant, date, p);

        } catch (IOException e) {
            String s = String.format("Could not create storage dir for tenant: %s and date: %s",
                                     tenant, date);
            throw new UncheckedIOException(s, e);
        }

        Env<ByteBuffer> env = Env.create()
                .setMapSize(GIBIBYTES.toBytes(10))
                .setMaxDbs(dataDbis.length + indices.length) // [data, index] dbi for each hour
                .setMaxReaders(4)
                .open(p.toFile(), MDB_WRITEMAP, MDB_NOSYNC);

        for (int i = 0; i < 24; i++) {
            String hour = i < 10 ? "0" + i : Integer.toString(i);
            Dbi<ByteBuffer> data = env.openDbi(hour, MDB_CREATE);
            dataDbis[i] = data;
//            Dbi<ByteBuffer> index = env.openDbi(hour, MDB_CREATE, MDB_INTEGERKEY, MDB_DUPSORT, MDB_DUPFIXED);
            Dbi<ByteBuffer> index = env.openDbi(hour, MDB_CREATE, MDB_DUPSORT, MDB_DUPFIXED);
            indices[i] = index;
        }

        return env;
    }

}
