package com.risen.helper.cache;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/6/30 10:28
 */
public abstract class CacheMinuteDataAbstract<K, V, L> implements Serializable {

    private Long expire = 4L;
    private Integer maximumSize = 100000;
    private Integer initialCapacity = 1024;

    private static List<CacheMinuteDataAbstract> implTree = new ArrayList<>();

    protected LoadingCache<K, V> cache;

    protected abstract V getOneByKey(K k);

    protected abstract void loadCache(L dataList);

    public abstract void loadCache();

    public CacheMinuteDataAbstract(Long expire, Integer maximumSize, Integer initialCapacity) {
        implTree.add(this);
        Optional.ofNullable(expire).ifPresent(s -> {
            this.expire = s;
        });
        Optional.ofNullable(maximumSize).ifPresent(s -> {
            this.maximumSize = s;
        });
        Optional.ofNullable(initialCapacity).ifPresent(s -> {
            this.initialCapacity = s;
        });
        cache = Optional.ofNullable(cache).orElse(Caffeine.newBuilder()
                .expireAfterWrite(this.expire, TimeUnit.MINUTES)
                .initialCapacity(this.initialCapacity)
                .maximumSize(this.maximumSize)
                .build(new CacheLoader<K, V>() {
                    @Override
                    public @Nullable V load(@NonNull K k) throws Exception {
                        return getOneByKey(k);
                    }
                }));
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void put(K key, V obj) {
        cache.put(key, obj);
    }

    public Boolean containKey(K key) {
        Predicate<V> predicate = s -> ObjectUtils.isEmpty(s);
        V t = cache.get(key);
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


    public static List<CacheMinuteDataAbstract> getImplTree() {
        return implTree;
    }
}
