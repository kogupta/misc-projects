package com.oracle.emcsas.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public final class SimpleLRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int cacheSize;

    public SimpleLRUCache(int cacheSize) {
        super(16, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() >= cacheSize;
    }

    public static <K, V> SimpleLRUCache<K, V> newInstance(int size) {
        return new SimpleLRUCache<>(size);
    }
}