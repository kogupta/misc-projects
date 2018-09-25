package org.kogupta.diskStore.utils;

enum EventTimeProvider implements TimeProvider {
    INSTANCE;

    private long curr;

    @Override
    public long currentTimeMillis() {
        return curr;
    }

    @Override
    public void updateCurrentTimeMillis(long millis) {
        curr = Math.max(curr, millis);
    }
}
