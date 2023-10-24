package com.chadsoft.murci.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Date;

@Table
@Data
@Builder
public class MurcielagoData {

    @Id
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
