package com.chadsoft.murci.persistence.repo;

import com.chadsoft.murci.persistence.entity.MurcielagoData;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface MurcielagoDataRepository extends R2dbcRepository<MurcielagoData, Long> {

}