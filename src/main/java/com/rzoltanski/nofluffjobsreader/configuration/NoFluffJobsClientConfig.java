package com.rzoltanski.nofluffjobsreader.configuration;

import com.rzoltanski.nofluffjobsreader.client.NoFluffJobsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class NoFluffJobsClientConfig {

    @Bean
    NoFluffJobsClient noFLuffJobsClient(NoFluffJobsClientProperties properties) {

        WebClient webClient = WebClient
                .builder()
                .baseUrl(properties.getBaseUrl())
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(properties.getResponseMaxSize()))
                .build();

        return HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build()
                .createClient(NoFluffJobsClient.class);
    }
}
