package com.chadsoft.murci.vinwiki;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

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

    public Mono<VinWikiResponse> getDataFromGlobalMurcielagoListMock() {
        return Mono.just(VinWikiResponse.builder()
                .vehicles(
                        List.of(
                                Vehicle.builder()
                                        .vin("ZHWBE16S06LA01790")
                                        .build(),
                                Vehicle.builder()
                                        .vin("AAAAAAAAAAAAAAAAA")
                                        .build(),
                                Vehicle.builder()
                                        .vin("ZHWBE16S06LA01790")
                                        .build()
                        )
                )
                .build());
    }
}