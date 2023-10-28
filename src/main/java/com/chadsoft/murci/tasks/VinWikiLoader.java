package com.chadsoft.murci.tasks;

import com.chadsoft.murci.entity.MurcielagoData;
import com.chadsoft.murci.vinwiki.Vehicle;
import com.chadsoft.murci.vinwiki.VinWikiClient;
import com.chadsoft.murci.service.MurcielagoDataService;
import com.chadsoft.murci.service.MurcielagoInvalidDataService;
import com.chadsoft.murci.vin.DecodedVinInfoFactory;
import com.chadsoft.murci.vin.exception.VinValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class VinWikiLoader {

    private final VinWikiClient vinWikiClient;
    private final MurcielagoDataService murcielagoDataService;
    private final MurcielagoInvalidDataService murcielagoInvalidDataService;

    @Scheduled(cron = "${scheduled-tasks.vin-wiki-cron}")
    @EventListener(ApplicationReadyEvent.class)
    public void loadDataFromVinWiki() {
        final long startTime = System.currentTimeMillis();
        log.warn("Starting VinWiki data load at {}", startTime);

        vinWikiClient.getDataFromGlobalMurcielagoListMock()
                .flatMapMany(response -> Flux.fromIterable(response.getVehicles()))
                .map(Vehicle::getVin)
                .doOnNext(this::validateAndSaveToDb)
                .doOnComplete(() -> log.info("Ending VinWiki data load, time: {}", System.currentTimeMillis() - startTime))
                .subscribe();
    }

    private void validateAndSaveToDb(String vin) {
        DecodedVinInfoFactory.decodeFromVin(vin)
                .flatMap(murcielagoDataService::save)
                .onErrorResume(VinValidationException.class, vinValidationException -> murcielagoInvalidDataService.save(vinValidationException).then(Mono.just(MurcielagoData.builder().build())))
                .subscribe();
    }
}