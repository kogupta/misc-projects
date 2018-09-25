package com.oracle.emcsas.utils;

public interface TimeProvider {
    long currentTimeMillis();
    void updateCurrentTimeMillis(long millis);

    static TimeProvider instance() {
        return EventTimeProvider.INSTANCE;
    }
}
