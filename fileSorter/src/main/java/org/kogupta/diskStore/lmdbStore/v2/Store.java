package org.kogupta.diskStore.lmdbStore.v2;

import com.google.common.flogger.FluentLogger;
import org.kogupta.diskStore.Pojo2;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static org.kogupta.diskStore.lmdbStore.v2.TimeFunctions.*;

public final class Store {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

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
        assert from <= to;

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
            for (long i = from; i < to; i = startOfNextDay(i)) {
                long _to = Math.min(endOfDay(i), to);
                List<Pojo2> xs = read(i, _to, tenant, secondaryKey);
                result.addAll(xs);
            }

            return result;
        }
    }

    public int countKeys(long from, long to, String tenant, String secondaryKey) {
        assert from <= to;

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
            for (long i = from; i < to; i = startOfNextDay(i)) {
                long _to = Math.min(endOfDay(i), to);
                result += countKeys(i, _to, tenant, secondaryKey);
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
}
