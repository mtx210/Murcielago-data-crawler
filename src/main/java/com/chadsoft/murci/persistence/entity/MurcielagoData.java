package com.chadsoft.murci.persistence.entity;

import com.chadsoft.murci.vin.enums.VinType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


@Table
@Data
@Builder
public class MurcielagoData {

    @Id
    private Long id;
    private String vin;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastModified;
    private VinType vinType;
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
