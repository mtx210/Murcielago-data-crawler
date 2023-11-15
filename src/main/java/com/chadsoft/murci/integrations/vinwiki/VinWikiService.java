package com.chadsoft.murci.integrations.vinwiki;

import lombok.RequiredArgsConstructor;
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
                .flatMap(vinWikiResponse -> Flux.fromIterable(vinWikiResponse.getVehicles()))
                .map(Vehicle::getVin);
    }

    private List<String> getMurcielagoUserListsUrls() {
        return properties.getMurcielagoUserListsIds().stream()
                .map(listId -> String.format(properties.getUrlTemplate(), listId))
                .toList();
    }
}
