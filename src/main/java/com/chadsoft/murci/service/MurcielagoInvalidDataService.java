package com.chadsoft.murci.service;

import com.chadsoft.murci.entity.MurcielagoInvalidData;
import com.chadsoft.murci.repo.MurcielagoInvalidDataRepository;
import com.chadsoft.murci.vin.exception.VinValidationException;
import com.sun.jdi.request.DuplicateRequestException;
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

    public Mono<MurcielagoInvalidData> save(VinValidationException exception) {
        return murcielagoInvalidDataRepository.save(map(exception))
                .onErrorResume(DuplicateKeyException.class, e -> {
                    log.info("Skipping saving already existing invalid VIN in db: {}", exception.getVin());
                    return Mono.empty();
                });
    }

    private MurcielagoInvalidData map(VinValidationException exception) {
        return MurcielagoInvalidData.builder()
                .vin(exception.getVin())
                .validationFailReason(exception.getValidationFailReason())
                .build();
    }
}