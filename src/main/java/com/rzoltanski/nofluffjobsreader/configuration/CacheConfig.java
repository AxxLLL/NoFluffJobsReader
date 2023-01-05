package com.rzoltanski.nofluffjobsreader.configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    CacheManager cacheManager(CacheProperties properties) {

        List<CaffeineCache> caches = properties.getCache()
                .entrySet()
                .stream()
                .map(entry -> buildCache(entry.getKey(), entry.getValue().getTimeToLive()))
                .toList();

        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(caches);
        return manager;
    }

    private CaffeineCache buildCache(String cacheName, Duration ttlDuration) {
        Cache<Object, Object> cacheTTL = Caffeine.newBuilder()
                .expireAfterWrite(ttlDuration)
                .build();
        return new CaffeineCache(cacheName, cacheTTL);
    }
}
