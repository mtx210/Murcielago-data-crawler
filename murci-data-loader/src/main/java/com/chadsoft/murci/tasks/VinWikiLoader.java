package com.chadsoft.murci.tasks;

import com.chadsoft.murci.integration.vinwiki.Vehicle;
import com.chadsoft.murci.integration.vinwiki.VinWikiClient;
import com.chadsoft.murci.model.vin.VinFactory;
import com.chadsoft.murci.persistence.repo.MurcielagoDataRepository;
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
    private final MurcielagoDataRepository murcielagoDataRepository;

    @Scheduled(cron = "${scheduled-tasks.vin-wiki-cron}")
    public void loadDataFromVinWiki() {
        long startTime = System.currentTimeMillis();
        log.info("Starting vinwiki data load");

        vinWikiClient.getDataFromGlobalMurcielagoList()
                .flatMapMany(response -> Flux.fromIterable(response.getVehicles()))
                .map(Vehicle::getVin)
                .doOnNext(vin -> )
                .doOnComplete(() -> );
    }

    private void handleVin(String vin) {
        VinFactory.createFromVin(vin)
    }
}