package com.chadsoft.murci.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(2048 * 1024))
                .build();
    }
}