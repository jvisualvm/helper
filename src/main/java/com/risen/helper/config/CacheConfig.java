package com.risen.helper.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.risen.helper.util.LogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangxin
 * @version 1.0
 * @date 2022/3/16 13:44
 */
@Configuration
@ConditionalOnProperty(prefix = "helper.cache", name = "switch", havingValue = "true")
public class CacheConfig {


    @Value("${cache.capacity:100}")
    private Integer capacity;

    @Value("${cache.maxsize:1000}")
    private Integer maxsize;

    @Value("${cache.expire:2}")
    private Integer expire;

    @Bean
    public Cache<Object, Object> caffeineCache() {
        LogUtil.info("open Caffeine|capacity:{},maxsize:{},expire:{}", capacity, maxsize, expire);
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterWrite(expire, TimeUnit.HOURS)
                // 初始的缓存空间大小
                .initialCapacity(capacity)
                // 缓存的最大条数
                .maximumSize(maxsize)
                .build();
    }


}
