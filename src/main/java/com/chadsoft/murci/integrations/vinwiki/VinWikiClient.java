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
                .doOnNext(vinWikiResponse -> logResults(vinWikiResponse, userListUrl));
    }

    private void logResults(VinWikiResponse vinWikiResponse, String userListUrl) {
        if (vinWikiResponse.getVehicles() != null) {
            log.info("Retrieved {} vehicle data from list {}",
                    vinWikiResponse.getVehicles().size(),
                    userListUrl);
        } else {
            log.warn("User list of id: {} contains no vehicle data", userListUrl);
        }
    }
}