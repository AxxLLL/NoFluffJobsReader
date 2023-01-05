package com.rzoltanski.nofluffjobsreader.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.no-fluff-jobs-client")
public class NoFluffJobsClientProperties {

    private String baseUrl;

    private int responseMaxSize;

    private boolean useCache = true;
}
