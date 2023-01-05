package com.rzoltanski.nofluffjobsreader.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties("application")
public class CacheProperties {

    private Map<String, Cache> cache;

    @Data
    public static class Cache {

        private Duration timeToLive;
    }
}
