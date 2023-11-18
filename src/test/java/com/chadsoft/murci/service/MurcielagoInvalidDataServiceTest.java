package com.chadsoft.murci.service;

import com.chadsoft.murci.persistence.entity.MurcielagoInvalidData;
import com.chadsoft.murci.persistence.repo.MurcielagoInvalidDataRepository;
import com.chadsoft.murci.tasks.LoadResult;
import com.chadsoft.murci.vin.exception.VinValidationException;
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
class MurcielagoInvalidDataServiceTest {

    @Mock
    private MurcielagoInvalidDataRepository murcielagoInvalidDataRepository;

    @InjectMocks
    private MurcielagoInvalidDataService murcielagoInvalidDataService;

    @Test
    void saveInvalidVinInfoInDb_shouldSaveInvalidVinInDb() {
        when(murcielagoInvalidDataRepository.save(any()))
                .thenReturn(Mono.just(MurcielagoInvalidData.builder().build()));

        StepVerifier.create(murcielagoInvalidDataService.saveInvalidVinInfoInDb(buildVinValidationException()))
                .expectNext(LoadResult.INVALID_RECORD_SAVED)
                .verifyComplete();
    }

    @Test
    void saveVinInfoInDb_shouldHandleDuplicateVinInDb() {
        when(murcielagoInvalidDataRepository.save(any()))
                .thenReturn(Mono.error(new DuplicateKeyException("")));

        StepVerifier.create(murcielagoInvalidDataService.saveInvalidVinInfoInDb(buildVinValidationException()))
                .expectNext(LoadResult.INVALID_RECORD_SKIPPED)
                .verifyComplete();
    }

    private VinValidationException buildVinValidationException() {
        return new VinValidationException("", "");
    }
}