package org.kogupta.diskStore.lmdbStore.v2;

import com.google.common.flogger.FluentLogger;
import org.kogupta.diskStore.Pojo2;
import org.kogupta.diskStore.utils.Bucket;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public final class Store {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private final Path dir;
    private final Map<TenantDatePair, PartitionedTenantStore> date2Store;

    public Store(Path dir) {
        this.dir = dir;
        date2Store = new LinkedHashMap<>();
    }

    public void bulkAdd(List<Pojo2> xs) {
        Map<TenantDatePair, List<Pojo2>> map = xs.stream().collect(groupingBy(TenantDatePair::from));

        for (Map.Entry<TenantDatePair, List<Pojo2>> kv : map.entrySet()) {
            TenantDatePair key = kv.getKey();
            List<Pojo2> pojos = kv.getValue();
            logger.atInfo().log("To persist: tenant: %s, date: %s, count: %d",
                                key.tenant, key.date, pojos.size());

            PartitionedTenantStore pStore = date2Store.computeIfAbsent(key, this::createPartitionedStore);
            pStore.bulkAdd(pojos);
        }
    }

    // tenant aware addition
    public void bulkAdd(String tenant, String date, List<Pojo2> xs) {
        if (xs == null || xs.isEmpty()) return;

        TenantDatePair key = TenantDatePair.of(tenant, date);
        PartitionedTenantStore pStore = date2Store.computeIfAbsent(key, this::createPartitionedStore);
        pStore.bulkAdd(xs);
    }

    private PartitionedTenantStore createPartitionedStore(TenantDatePair t) {
        return new PartitionedTenantStore(dir, t.tenant, t.date);
    }

    public List<Pojo2> read(long from, long to, String secondaryKey) {


    }

    public void dropDb(int hour) {

    }

    private static final class TenantDatePair {
        final String tenant;
        final String date;

        private TenantDatePair(String tenant, String date) {
            this.tenant = tenant;
            this.date = date;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TenantDatePair that = (TenantDatePair) o;
            return tenant.equals(that.tenant) && date.equals(that.date);
        }

        @Override
        public int hashCode() {
            return 31 * tenant.hashCode() + date.hashCode();
        }

        @Override
        public String toString() {
            return "Tenant: " + tenant + ", date: " + date;
        }

        static TenantDatePair from(Pojo2 pojo2) {
            String date = Bucket.create(pojo2.getTimestamp()).date;
            return new TenantDatePair(pojo2.getTenant(), date);
        }

        public static TenantDatePair of(String tenant, String date) {
            return new TenantDatePair(tenant, date);
        }
    }
}
