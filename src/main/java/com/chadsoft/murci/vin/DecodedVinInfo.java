package com.chadsoft.murci.vin;

import com.chadsoft.murci.vin.enums.BodyType;
import com.chadsoft.murci.vin.enums.EngineVariant;
import com.chadsoft.murci.vin.enums.Market;
import com.chadsoft.murci.vin.enums.TransmissionVariant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DecodedVinInfo implements Comparable<DecodedVinInfo> {

    private String fullVin;
    private boolean isOldStandard;
    private Integer modelYear;
    private BodyType bodyType;
    private EngineVariant engineVariant;
    private TransmissionVariant transmissionVariant;
    private Integer serialNumber;
    private Market market;
    private Boolean isFacelift;
    private Boolean isReventon;
    private Boolean isSuperVeloce;

    @Override
    public int compareTo(DecodedVinInfo o) {
        return this.modelYear.compareTo(o.modelYear) == 0 ? 0 : this.serialNumber.compareTo(o.serialNumber);
    }
}