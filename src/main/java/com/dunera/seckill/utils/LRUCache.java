package com.dunera.seckill.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author lyx
 * @date 2018/11/21
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int maxSize;

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > maxSize;
    }

    public LRUCache(int maxSize) {
        this(maxSize, 16, 0.75F, false);
    }

    public LRUCache(int maxSize, int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
        this.maxSize = maxSize;
    }
}
