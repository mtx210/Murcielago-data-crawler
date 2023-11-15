package com.chadsoft.murci.integrations.vinwiki;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
class VinWikiClient {

    private final WebClient webClient;

    public Mono<VinWikiResponse> getVehicleDataFromUserList(String userListUrl) {
        return webClient
                .get()
                .uri(userListUrl)
                .retrieve()
                .toEntity(VinWikiResponse.class)
                .mapNotNull(HttpEntity::getBody)
                .doOnNext(vinWikiResponse ->
                        log.info("Retrieved {} vehicle data from list {}",
                                vinWikiResponse.getVehicles().size(),
                                userListUrl)
                );
    }
}