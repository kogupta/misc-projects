package org.kogupta.diskStore.lmdbStore.v2;

import org.kogupta.diskStore.Pojo2;
import org.kogupta.diskStore.utils.Bucket;

final class TenantDatePair {
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
