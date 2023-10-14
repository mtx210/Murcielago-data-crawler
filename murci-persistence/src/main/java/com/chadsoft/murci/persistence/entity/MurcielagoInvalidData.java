package com.chadsoft.murci.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "murcielago_invalid_data")
@Data
public class MurcielagoInvalidData {

    private Long id;
    private String vin;
    private String validationFailReason;
}
