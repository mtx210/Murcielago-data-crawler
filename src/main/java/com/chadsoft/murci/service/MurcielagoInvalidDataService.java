package com.chadsoft.murci.service;

import com.chadsoft.murci.persistence.entity.MurcielagoInvalidData;
import com.chadsoft.murci.persistence.repo.MurcielagoInvalidDataRepository;
import com.chadsoft.murci.tasks.LoadResult;
import com.chadsoft.murci.vin.exception.VinValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MurcielagoInvalidDataService {

    private final MurcielagoInvalidDataRepository murcielagoInvalidDataRepository;

    public Mono<LoadResult> save(VinValidationException exception) {
        return murcielagoInvalidDataRepository.save(map(exception))
                .map(murcielagoInvalidData -> LoadResult.INVALID_RECORD_SAVED)
                .onErrorResume(DuplicateKeyException.class, e -> {
                    log.info("Skipping saving already existing invalid VIN in db: {}", exception.getVin());
                    return Mono.just(LoadResult.INVALID_RECORD_SKIPPED);
                });
    }

    private MurcielagoInvalidData map(VinValidationException exception) {
        return MurcielagoInvalidData.builder()
                .vin(exception.getVin())
                .validationFailReason(exception.getValidationFailReason())
                .build();
    }
}