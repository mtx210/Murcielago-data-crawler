package com.chadsoft.murci.tasks;

import com.chadsoft.murci.integration.vinwiki.Vehicle;
import com.chadsoft.murci.integration.vinwiki.VinWikiClient;
import com.chadsoft.murci.model.vin.DecodedVinInfoFactory;
import com.chadsoft.murci.model.vin.exception.VinValidationException;
import com.chadsoft.murci.service.MurcielagoDataService;
import com.chadsoft.murci.service.MurcielagoInvalidDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@Slf4j
@RequiredArgsConstructor
public class VinWikiLoader {

    private final VinWikiClient vinWikiClient;
    private final MurcielagoDataService murcielagoDataService;
    private final MurcielagoInvalidDataService murcielagoInvalidDataService;

    @Scheduled(cron = "${scheduled-tasks.vin-wiki-cron}")
    public void loadDataFromVinWiki() {
        final long startTime = System.currentTimeMillis();
        log.info("Starting VinWiki data load");

        vinWikiClient.getDataFromGlobalMurcielagoList()
                .flatMapMany(response -> Flux.fromIterable(response.getVehicles()))
                .map(Vehicle::getVin)
                .doOnNext(this::validateAndSaveToDb)
                .doOnComplete(() -> log.info("Ending VinWiki data load, time: {}", System.currentTimeMillis() - startTime))
                .subscribe();
    }

    private void validateAndSaveToDb(String vin) {
        DecodedVinInfoFactory.decodeFromVin(vin)
                .flatMap(murcielagoDataService::save)
                .doOnError(VinValidationException.class, murcielagoInvalidDataService::save)
                .subscribe();
    }
}