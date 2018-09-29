package org.kogupta.diskStore.lmdbStore;

public final class WriteParam<T> {
    final String tenant;
    final long timestamp;
    final T payload;

    public WriteParam(String tenant, long timestamp, T payload) {
        this.tenant = tenant;
        this.timestamp = timestamp;
        this.payload = payload;
    }
}
