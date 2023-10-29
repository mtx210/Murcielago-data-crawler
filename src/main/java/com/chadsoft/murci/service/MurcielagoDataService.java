package com.chadsoft.murci.service;

import com.chadsoft.murci.entity.MurcielagoData;
import com.chadsoft.murci.repo.MurcielagoDataRepository;
import com.chadsoft.murci.tasks.LoadResult;
import com.chadsoft.murci.vin.DecodedVinInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Service
@Slf4j
public class MurcielagoDataService {

    private final MurcielagoDataRepository murcielagoDataRepository;

    public MurcielagoDataService(MurcielagoDataRepository murcielagoDataRepository) {
        this.murcielagoDataRepository = murcielagoDataRepository;
    }

    public Mono<LoadResult> save(DecodedVinInfo decodedVinInfo) {
        return murcielagoDataRepository.save(map(decodedVinInfo))
                .map(murcielagoData -> LoadResult.VALID_RECORD_SAVED)
                .onErrorResume(DuplicateKeyException.class, e -> {
                    log.info("Skipping saving already existing VIN in db: {}", decodedVinInfo.getFullVin());
                    return Mono.just(LoadResult.VALID_RECORD_SKIPPED);
                });
    }

    private MurcielagoData map(DecodedVinInfo decodedVinInfo) {
        LocalDateTime dateNow = LocalDateTime.now();

        return MurcielagoData.builder()
                .vin(decodedVinInfo.getFullVin())
                .dateCreated(dateNow)
                .dateLastModified(dateNow)
                .isOldStandard(decodedVinInfo.isOldStandard())
                .modelYear(decodedVinInfo.getModelYear())
                .bodyType(decodedVinInfo.getBodyType().toString())
                .engine(decodedVinInfo.getEngineVariant().toString())
                .transmission(decodedVinInfo.getTransmissionVariant().toString())
                .serialNumber(decodedVinInfo.getSerialNumber())
                .market(decodedVinInfo.getMarket().toString())
                .isFacelift(decodedVinInfo.getIsFacelift())
                .isReventon(decodedVinInfo.getIsReventon())
                .isSuperveloce(decodedVinInfo.getIsSuperVeloce())
                .build();
    }
}