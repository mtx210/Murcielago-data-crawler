package com.chadsoft.murci.repo;

import com.chadsoft.murci.entity.MurcielagoData;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface MurcielagoDataRepository extends R2dbcRepository<MurcielagoData, Long> {

}