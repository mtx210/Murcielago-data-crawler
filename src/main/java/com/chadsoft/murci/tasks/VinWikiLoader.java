package com.chadsoft.murci.tasks;

import com.chadsoft.murci.integrations.vinwiki.VinWikiService;
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
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class VinWikiLoader {

    private final VinWikiService vinWikiService;
    private final MurcielagoDataService murcielagoDataService;
    private final MurcielagoInvalidDataService murcielagoInvalidDataService;

    @Scheduled(cron = "${scheduled-tasks.vin-wiki-cron}")
    @EventListener(ApplicationReadyEvent.class)
    public void loadDataFromVinWiki() {
        final long startTime = System.currentTimeMillis();
        log.info("Starting VinWiki data load");

        vinWikiService.getVinsFromVinWikiUserLists()
                .flatMap(this::validateAndSaveToDb)
                .reduce(new LoadStatistics(), this::buildStatistics)
                .doOnSuccess(loadStatistics -> logStatistics(loadStatistics, startTime))
                .subscribe();
    }

    private Mono<LoadResult> validateAndSaveToDb(String vin) {
        return DecodedVinInfoFactory.decodeFromVin(vin)
                .flatMap(murcielagoDataService::saveVinInfoInDb)
                .onErrorResume(VinValidationException.class, murcielagoInvalidDataService::saveInvalidVinInfoInDb);
    }

    private LoadStatistics buildStatistics(LoadStatistics loadStatistics, LoadResult loadResult) {
        switch (loadResult) {
            case VALID_RECORD_SAVED -> loadStatistics.incrementValidRecordsLoaded();
            case INVALID_RECORD_SAVED -> loadStatistics.incrementInvalidRecordsLoaded();
            case VALID_RECORD_SKIPPED -> loadStatistics.incrementValidRecordsSkipped();
            case INVALID_RECORD_SKIPPED -> loadStatistics.incrementInvalidRecordsSkipped();
        }
        return loadStatistics;
    }

    private void logStatistics(LoadStatistics loadStatistics, long startTime) {
        log.info("Ending VinWiki data load, time: {} ms", System.currentTimeMillis() - startTime);
        log.info("VinWiki load statistics:\nNew valid records saved: {}\nNew invalid records saved: {}\nValid records skipped: {}\nInvalid records skipped: {}",
                loadStatistics.getValidRecordsSaved(),
                loadStatistics.getInvalidRecordsSaved(),
                loadStatistics.getValidRecordsSkipped(),
                loadStatistics.getInvalidRecordsSkipped()
        );
    }
}