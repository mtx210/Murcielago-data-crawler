package com.chadsoft.murci.service;

import com.chadsoft.murci.persistence.entity.MurcielagoData;
import com.chadsoft.murci.persistence.repo.MurcielagoDataRepository;
import com.chadsoft.murci.tasks.LoadResult;
import com.chadsoft.murci.vin.DecodedVinInfo;
import com.chadsoft.murci.vin.enums.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MurcielagoDataServiceTest {

    @Mock
    private MurcielagoDataRepository murcielagoDataRepository;

    @InjectMocks
    private MurcielagoDataService murcielagoDataService;

    @Test
    void saveVinInfoInDb_shouldSaveVinInDb() {
        when(murcielagoDataRepository.save(any()))
                .thenReturn(Mono.just(MurcielagoData.builder().build()));

        StepVerifier.create(murcielagoDataService.saveVinInfoInDb(buildDecodedVinInfo()))
                .expectNext(LoadResult.VALID_RECORD_SAVED)
                .verifyComplete();
    }

    @Test
    void saveVinInfoInDb_shouldHandleDuplicateVinInDb() {
        when(murcielagoDataRepository.save(any()))
                .thenReturn(Mono.error(new DuplicateKeyException("")));

        StepVerifier.create(murcielagoDataService.saveVinInfoInDb(buildDecodedVinInfo()))
                .expectNext(LoadResult.VALID_RECORD_SKIPPED)
                .verifyComplete();
    }

    private DecodedVinInfo buildDecodedVinInfo() {
        return DecodedVinInfo.builder()
                .fullVin("")
                .vinType(VinType.MY2003)
                .modelYear(1)
                .bodyType(BodyType.COUPE)
                .engineVariant(EngineVariant.V12_62_580HP)
                .transmissionVariant(TransmissionVariant.MANUAL)
                .serialNumber(1)
                .market(Market.EUROPE)
                .isFacelift(false)
                .isReventon(false)
                .isSuperVeloce(false)
                .build();
    }
}