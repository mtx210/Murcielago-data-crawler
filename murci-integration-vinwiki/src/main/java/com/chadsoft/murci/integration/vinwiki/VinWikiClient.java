package com.chadsoft.murci.integration.vinwiki;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class VinWikiClient {

    @Value("${integrations.vin-wiki.global-murcielago-list-url}")
    private String apiUrl;

    private final WebClient webClient;

    public Mono<VinWikiResponse> getDataFromGlobalMurcielagoList() {
        return webClient
                .get()
                .uri(apiUrl)
                .retrieve()
                .toEntity(VinWikiResponse.class)
                .mapNotNull(HttpEntity::getBody);
    }
}