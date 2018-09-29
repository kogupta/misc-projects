package org.kogupta.diskStore.lmdbStore;

public final class ReadRequest {
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
