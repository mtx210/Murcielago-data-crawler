package com.chadsoft.murci.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Data
@Builder
public class MurcielagoInvalidData {

    @Id
    private Long id;
    private String vin;
    private String validationFailReason;
    private Boolean isManuallyReviewed;
    private String validVinFound;
}