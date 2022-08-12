package com.risen.helper.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
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
 * @date 2022/3/23 15:53
 */
@Deprecated
public abstract class AgentCacheAbstract<K, V> implements Serializable {

    private Integer expire = 4;
    private Integer maximumSize = 100000;
    private Integer initialCapacity = 1024;

    private static List<AgentCacheAbstract> implTree = new ArrayList<>();

    public Cache<K, V> cache;

    public abstract void loadCache();

    public AgentCacheAbstract(Integer expire, Integer maximumSize, Integer initialCapacity) {
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
                .expireAfterWrite(this.expire, TimeUnit.HOURS)
                .initialCapacity(this.initialCapacity)
                .maximumSize(this.maximumSize)
                .build(new CacheLoader<K, V>() {
                    @Override
                    public @Nullable V load(@NonNull K k) throws Exception {
                        return get(k);
                    }
                }));
    }

    public V get(K key) {
        Predicate<V> predicate = s -> ObjectUtils.isEmpty(s);
        V t = cache.getIfPresent(key);
        if (predicate.test(t)) {
            loadCache();
        }
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




    public static List<AgentCacheAbstract> getImplTree() {
        return implTree;
    }
}
