package org.kogupta.diskStore.lmdbStore;

import com.google.common.flogger.FluentLogger;
import org.kogupta.diskStore.Pojo;
import org.kogupta.diskStore.utils.AppMetrics;
import org.kogupta.diskStore.utils.Functions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.kogupta.diskStore.utils.Functions.toMillis;

public final class Reader implements Runnable {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    public static final LmdbStore.ReadRequest POISON_PILL = new LmdbStore.ReadRequest("--end-now--", 1, 2);

    private final BlockingQueue<LocalDateTime> readQ;
    private final BlockingQueue<LmdbStore.ReadRequest> deleteQ;
    private final LmdbStore store;
    private final String[] tenants;

    public Reader(BlockingQueue<LocalDateTime> queue,
                  BlockingQueue<LmdbStore.ReadRequest> deleteQ,
                  LmdbStore store,
                  int tenantCount) {
        this.readQ = queue;
        this.deleteQ = deleteQ;
        this.store = store;
        this.tenants = Functions.tenants(tenantCount);
    }

    @Override
    public void run() {
        LocalDateTime time;
        try {
            while ((time = readQ.take()) != Writer.POISON_PILL) {
                long to = toMillis(time);
                long minus5 = to - MINUTES.toMillis(5);
                long minus10 = to - MINUTES.toMillis(10);
                logger.atInfo().log("Reading from [t-5] to [t], t=%s", time.toString());
                read(minus5, to);
                logger.atInfo().log("Reading from [t-10] to [t-5], t=%s", time.toString());
                read(minus10, minus5);
            }
        } catch (InterruptedException e) {
            logger.atSevere().withCause(e).log("Exception while removing head from event-time queue");
        }

        deleteQ.add(POISON_PILL);

        RWTest.blocker.countDown();
    }

    private void read(long from, long to) {
        for (String tenant : tenants) {
            LmdbStore.ReadRequest r = new LmdbStore.ReadRequest(tenant, from, to);
            long t0 = System.nanoTime();
            int n = store.countKeysInRange(r);
            long t1 = System.nanoTime();
            List<Pojo> pojos = store.read(r, Functions::fromByteBuffer);
            long t2 = System.nanoTime();
            assert pojos.size() == n : "store inconsistent!";

            AppMetrics.newHistogram("kv-store:keyCount").update(NANOSECONDS.toMillis(t1 - t0));
            AppMetrics.newHistogram("kv-store:objectRead").update(NANOSECONDS.toMillis(t2 - t1));
            logger.atInfo().log("    key count: %,d", n);

            logger.atInfo().log("Sending delete request ...");
            deleteQ.add(r);
        }
    }
}
