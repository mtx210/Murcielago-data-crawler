package com.chadsoft.murci.integrations.vinwiki;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class VinWikiServiceTest {

    @Mock
    private VinWikiProperties vinWikiProperties;

    @Mock
    private VinWikiClient vinWikiClient;

    @InjectMocks
    private VinWikiService vinWikiService;

    @Test
    void getVinsFromVinWikiUserLists_shouldSucceed() {
        final String urlMock = "https://host/api/%s";
        final String listId1Mock = "id1";
        final String listId2Mock = "id2";

        Mockito.when(vinWikiProperties.getUrlTemplate())
                .thenReturn(urlMock);

        Mockito.when(vinWikiProperties.getMurcielagoUserListsIds())
                .thenReturn(List.of(listId1Mock, listId2Mock));

        Mockito.when(vinWikiClient.getVehicleDataFromUserList(String.format(urlMock, listId1Mock)))
                .thenReturn(Mono.just(VinWikiResponse.builder()
                        .vehicles(List.of(
                                Vehicle.builder()
                                        .vin("vin1")
                                        .build(),
                                Vehicle.builder()
                                        .vin("vin2")
                                        .build()
                        ))
                        .build()));

        Mockito.when(vinWikiClient.getVehicleDataFromUserList(String.format(urlMock, listId2Mock)))
                .thenReturn(Mono.just(VinWikiResponse.builder()
                        .vehicles(List.of(
                                Vehicle.builder()
                                        .vin("vin3")
                                        .build(),
                                Vehicle.builder()
                                        .vin("vin4")
                                        .build()
                        ))
                        .build()));

        StepVerifier.create(vinWikiService.getVinsFromVinWikiUserLists())
                .expectNext("vin1")
                .expectNext("vin2")
                .expectNext("vin3")
                .expectNext("vin4")
                .verifyComplete();
    }

    @Test
    void getVinsFromVinWikiUserLists_shouldFilterResponses() {
        final String urlMock = "https://host/api/%s";
        final String listId1Mock = "id1";
        final String listId2Mock = "id2";

        Mockito.when(vinWikiProperties.getUrlTemplate())
                .thenReturn(urlMock);

        Mockito.when(vinWikiProperties.getMurcielagoUserListsIds())
                .thenReturn(List.of(listId1Mock, listId2Mock));

        Mockito.when(vinWikiClient.getVehicleDataFromUserList(String.format(urlMock, listId1Mock)))
                .thenReturn(Mono.just(VinWikiResponse.builder()
                        .vehicles(null)
                        .build()));

        Mockito.when(vinWikiClient.getVehicleDataFromUserList(String.format(urlMock, listId2Mock)))
                .thenReturn(Mono.just(VinWikiResponse.builder()
                        .vehicles(List.of(
                                Vehicle.builder()
                                        .vin(null)
                                        .build(),
                                Vehicle.builder()
                                        .vin("")
                                        .build(),
                                Vehicle.builder()
                                        .vin("    ")
                                        .build(),
                                Vehicle.builder()
                                        .vin("vin1")
                                        .build()
                        ))
                        .build()));

        StepVerifier.create(vinWikiService.getVinsFromVinWikiUserLists())
                .expectNext("vin1")
                .verifyComplete();
    }
}