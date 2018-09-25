package org.kogupta.diskStore.utils;

public interface TimeProvider {
    static TimeProvider instance() {
        return EventTimeProvider.INSTANCE;
    }

    long currentTimeMillis();

    void updateCurrentTimeMillis(long millis);
}
