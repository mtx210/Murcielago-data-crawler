package com.chadsoft.murci.integrations.vinwiki;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VinWikiService {

    private final VinWikiProperties properties;
    private final VinWikiClient vinWikiClient;

    public Flux<String> getVinsFromVinWikiUserLists() {
        return Flux.fromIterable(getMurcielagoUserListsUrls())
                .flatMap(vinWikiClient::getVehicleDataFromUserList)
                .flatMap(this::extractVinsFromResponse);
    }

    private List<String> getMurcielagoUserListsUrls() {
        return properties.getMurcielagoUserListsIds().stream()
                .map(listId -> String.format(properties.getUrlTemplate(), listId))
                .toList();
    }

    private Flux<String> extractVinsFromResponse(VinWikiResponse vinWikiResponse) {
        if (vinWikiResponse.getVehicles() != null) {
            return Flux.fromIterable(vinWikiResponse.getVehicles())
                    .flatMap(vehicle -> {
                        if (StringUtils.isNotBlank(vehicle.getVin())) {
                            return Flux.just(vehicle.getVin());
                        } else {
                            return Flux.empty();
                        }
                    });
        } else {
            return Flux.empty();
        }
    }
}