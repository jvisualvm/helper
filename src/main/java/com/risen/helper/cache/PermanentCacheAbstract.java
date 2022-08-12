package com.risen.helper.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/30 10:28
 */
public abstract class PermanentCacheAbstract<K, V> implements Serializable {

    private Integer maximumSize = 100000;
    private Integer initialCapacity = 1024;

    private static List<PermanentCacheAbstract> implTree = new ArrayList<>();

    public PermanentCacheAbstract() {
        implTree.add(this);
    }

    protected Cache<K, V> cache = Caffeine.newBuilder()
            .expireAfterWrite(Long.MAX_VALUE, TimeUnit.DAYS)
            .initialCapacity(this.initialCapacity)
            .maximumSize(this.maximumSize)
            .build();

    public abstract void loadCache();

    public V get(K key) {
        return cache.getIfPresent(key);
    }

    public void put(K key, V obj) {
        cache.put(key, obj);
    }

    public Boolean containKey(K key) {
        Predicate<V> predicate = s -> ObjectUtils.isEmpty(s);
        V t = cache.getIfPresent(key);
        if (predicate.test(t)) {
            return false;
        }
        return true;
    }

    public Map<K, V> getAllCacheValue() {
        Map<K, V> result = cache.asMap();
        if (CollectionUtils.isEmpty(result)) {
            loadCache();
        }
        return cache.asMap();
    }

    public static List<PermanentCacheAbstract> getImplTree() {
        return implTree;
    }
}
