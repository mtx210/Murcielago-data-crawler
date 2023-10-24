package com.chadsoft.murci.service;

import com.chadsoft.murci.entity.MurcielagoInvalidData;
import com.chadsoft.murci.model.vin.exception.VinValidationException;
import com.chadsoft.murci.repo.MurcielagoInvalidDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MurcielagoInvalidDataService {

    private final MurcielagoInvalidDataRepository murcielagoInvalidDataRepository;

    public Mono<MurcielagoInvalidData> save(VinValidationException exception) {
        return murcielagoInvalidDataRepository.save(map(exception));
    }

    private MurcielagoInvalidData map(VinValidationException exception) {
        return MurcielagoInvalidData.builder()
                .vin(exception.getVin())
                .validationFailReason(exception.getValidationFailReason())
                .build();
    }
}