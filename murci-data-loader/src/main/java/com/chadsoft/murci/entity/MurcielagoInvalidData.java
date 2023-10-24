package com.chadsoft.murci.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Data
@Builder
public class MurcielagoInvalidData {

    private Long id;
    private String vin;
    private String validationFailReason;
}
