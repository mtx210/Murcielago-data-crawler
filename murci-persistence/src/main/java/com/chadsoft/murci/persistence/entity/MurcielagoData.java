package com.chadsoft.murci.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "murcielago_data")
@Data
public class MurcielagoData {

    private Long id;
    private String vin;
    private Date dateCreated;
    private Date dateLastModified;
    private Boolean isOldStandard;
    private Integer modelYear;
    private String bodyType;
    private String engine;
    private String transmission;
    private Integer serialNumber;
    private String market;
    private Boolean isFacelift;
    private Boolean isReventon;
    private Boolean isSuperveloce;
}
