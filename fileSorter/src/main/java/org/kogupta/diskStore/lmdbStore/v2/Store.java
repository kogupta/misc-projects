package org.kogupta.diskStore.lmdbStore.v2;

import com.google.common.flogger.FluentLogger;
import org.kogupta.diskStore.Pojo2;
import org.kogupta.diskStore.utils.Bucket;

import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.time.ZoneOffset.UTC;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.Collectors.groupingBy;

public final class Store {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    private static final long oneMilli = MILLISECONDS.toNanos(1);

    private final Path dir;
    private final Map<TenantDatePair, PartitionedTenantStore> date2Store;

    public Store(Path dir) {
        this.dir = dir;
        date2Store = new LinkedHashMap<>();
    }

    public void bulkAdd(List<Pojo2> xs) {
        if (xs == null || xs.isEmpty()) return;

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

    public List<Pojo2> read(long from, long to, String tenant, String secondaryKey) {
        assert from < to;

        LocalDate a = toLocalDate(from);
        LocalDate b = toLocalDate(to);

        if (a.equals(b)) {
            String date = asDate(a);
            TenantDatePair tdPair = TenantDatePair.of(tenant, date);
            PartitionedTenantStore store = date2Store.get(tdPair);
            if (store == null) {
                return Collections.emptyList();
            }

            return store.read(from, to, secondaryKey);
        } else {
            List<Pojo2> result = new ArrayList<>();
            long n = from;
            LocalDate i = a;
            while (!i.equals(b)) {
                long endOfDay = endOfDay(i);
                List<Pojo2> xs = read(n, endOfDay, tenant, secondaryKey);
                result.addAll(xs);
                n = endOfDay + 1;
                i = i.plusDays(1);
            }

            return result;
        }
    }

    public int countKeys(long from, long to, String tenant, String secondaryKey) {
        assert from < to;

        LocalDate a = toLocalDate(from);
        LocalDate b = toLocalDate(to);

        if (a.equals(b)) {
            String date = asDate(a);
            TenantDatePair tdPair = TenantDatePair.of(tenant, date);
            PartitionedTenantStore store = date2Store.get(tdPair);
            if (store == null) {
                return 0;
            }

            return store.countKeys(from, to, secondaryKey);
        } else {
            int result = 0;
            long n = from;
            LocalDate i = a;
            while (!i.equals(b)) {
                long endOfDay = endOfDay(i);
                result += countKeys(n, endOfDay, tenant, secondaryKey);
                n = endOfDay + 1;
                i = i.plusDays(1);
            }

            return result;
        }
    }

    public void closeAll() {
        logger.atInfo().log("closing db for all tenants and dates");
        date2Store.forEach((key, tenantStore) -> tenantStore.close());
    }

    public void dropData(String tenant, LocalDate date, int hour) {
        assert hour >= 0 && hour < 24;
        String _date = asDate(date);
        TenantDatePair key = TenantDatePair.of(tenant, _date);
        PartitionedTenantStore store = date2Store.get(key);
        if (store == null) {
            logger.atSevere().log("No partitioned store for tenant: %s, date: %s",
                                  tenant, _date);
            return;
        }

        store.dropDb(hour);
    }

    private static String asDate(LocalDate a) {
        String mon = a.getMonth().name().substring(0, 3);
        int n = a.getDayOfMonth();
        String _day = n < 10 ? "0" + n : Integer.toString(n);
        return a.getYear() + "-" + mon + "-" + _day;
    }

    static LocalDate toLocalDate(long millis) {
        return Instant.ofEpochMilli(millis).atOffset(UTC).toLocalDate();
    }

    static long toEpochMillis(LocalDate date) {
        return date.atStartOfDay().toInstant(UTC).toEpochMilli();
    }


    static long endOfDay(LocalDate date) {
        LocalDateTime time = date.plusDays(1).atStartOfDay().minusNanos(oneMilli);
        return time.toInstant(UTC).toEpochMilli();
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
