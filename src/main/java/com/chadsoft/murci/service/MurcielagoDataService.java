package com.chadsoft.murci.service;

import com.chadsoft.murci.entity.MurcielagoData;
import com.chadsoft.murci.repo.MurcielagoDataRepository;
import com.chadsoft.murci.vin.DecodedVinInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Date;


@Service
@Slf4j
public class MurcielagoDataService {

    private final MurcielagoDataRepository murcielagoDataRepository;

    public MurcielagoDataService(MurcielagoDataRepository murcielagoDataRepository) {
        this.murcielagoDataRepository = murcielagoDataRepository;
    }

    public Mono<MurcielagoData> save(DecodedVinInfo decodedVinInfo) {
//        try {
//            return murcielagoDataRepository.save(map(decodedVinInfo))
//                    .doOnNext(murcielagoData -> {
//                        System.out.println(murcielagoData);
//                    });
//        } catch (DuplicateKeyException e) {
//            log.info("Skipping saving already existing VIN in db: {}", decodedVinInfo.getFullVin());
//            return Mono.empty();
//        }

        return murcielagoDataRepository.save(map(decodedVinInfo))
                .onErrorResume(DuplicateKeyException.class, e -> {
                    log.info("Skipping saving already existing VIN in db: {}", decodedVinInfo.getFullVin());
                    return Mono.empty();
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