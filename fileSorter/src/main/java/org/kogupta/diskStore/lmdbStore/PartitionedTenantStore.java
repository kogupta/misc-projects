package org.kogupta.diskStore.lmdbStore;

import com.google.common.flogger.FluentLogger;
import org.kogupta.diskStore.Pojo2;
import org.kogupta.diskStore.utils.Bucket;
import org.kogupta.diskStore.utils.FileUtils;
import org.lmdbjava.Dbi;
import org.lmdbjava.Env;
import org.lmdbjava.Txn;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import static com.jakewharton.byteunits.BinaryByteUnit.GIBIBYTES;
import static java.nio.ByteOrder.nativeOrder;
import static org.lmdbjava.DbiFlags.*;
import static org.lmdbjava.EnvFlags.MDB_NOSYNC;
import static org.lmdbjava.EnvFlags.MDB_WRITEMAP;

public final class PartitionedTenantStore {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    private static final Comparator<ByteBuffer> cmp = Comparator.comparing(a -> a.order(nativeOrder()));

    private final Path storeDir;
    private final String tenant;
    private final String date;
    private final Env<ByteBuffer> env;
    private final Dbi<ByteBuffer>[] dataDbis; // hourly partition
    private final Dbi<ByteBuffer>[] indices;  // hourly partition

    public PartitionedTenantStore(Path parentDir, String tenant, String date) {
        this.tenant = tenant;
        this.date = date;
        this.storeDir = parentDir.resolve(tenant);
        this.dataDbis = new Dbi[24];
        this.indices = new Dbi[24];
        this.env = getOrCreate(storeDir);
    }

    public void bulkAdd(List<Pojo2> xs) {
        for (Pojo2 x : xs) {
            Bucket bucket = Bucket.create(x.getTimestamp());
            Dbi<ByteBuffer> data = dataDbis[bucket.hour];
            Dbi<ByteBuffer> index = indices[bucket.hour];

            ByteBuffer id = ByteBuffer.allocateDirect(64);
            id.asCharBuffer().put(x.getUuid());
            ByteBuffer value = ByteBuffer.allocateDirect(4 * 1024);
            x.writeToBB(value);
            value.flip();

            ByteBuffer indexKey = ByteBuffer.allocateDirect(Long.BYTES);
            indexKey.putLong(x.getTimestamp()).flip();

            try (Txn<ByteBuffer> txn = env.txnWrite()) {
                data.put(txn, id, value);
                index.put(txn, indexKey, id);
            }

        }


    }

    private Env<ByteBuffer> getOrCreate(Path p) {
        try {
            FileUtils.mkdir(p);
        } catch (IOException e) {
            String s = "Could not create storage dir for tenant: " + tenant;
            throw new UncheckedIOException(s, e);
        }

        Env<ByteBuffer> env = Env.create()
                .setMapSize(GIBIBYTES.toBytes(10))
                .setMaxDbs(dataDbis.length + indices.length + 1) // 2 [data, index] dbi for each hour, dict
                .setMaxReaders(4)
                .open(p.toFile(), MDB_WRITEMAP, MDB_NOSYNC);

        for (int i = 0; i < 24; i++) {
            String hour = i < 10 ? "0" + i : Integer.toString(i);
            Dbi<ByteBuffer> data = env.openDbi(hour, MDB_CREATE);
            dataDbis[i] = data;
            Dbi<ByteBuffer> index = env.openDbi(hour, MDB_CREATE, MDB_INTEGERKEY, MDB_DUPSORT, MDB_DUPFIXED);
            indices[i] = index;
        }

        return env;
    }

    public static final class Payload {
        final long timestamp;
        final ByteBuffer value;

        public Payload(long timestamp, ByteBuffer value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }

}
