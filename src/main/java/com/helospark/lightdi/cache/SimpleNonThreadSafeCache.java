package com.helospark.lightdi.cache;

import java.util.LinkedHashMap;

public class SimpleNonThreadSafeCache<T, U> extends LinkedHashMap<T, U> {
    private int maxSize;

    public SimpleNonThreadSafeCache(int maxSize) {
        super(maxSize);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<T, U> eldest) {
        return size() >= maxSize;
    }
}
