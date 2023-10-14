package com.chadsoft.murci.model.vin;

import com.chadsoft.murci.model.vin.enums.BodyType;
import com.chadsoft.murci.model.vin.enums.EngineVariant;
import com.chadsoft.murci.model.vin.enums.Market;
import com.chadsoft.murci.model.vin.enums.TransmissionVariant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class Vin implements Comparable<Vin> {

    private final String fullVin;
    private final boolean isOldStandard;
    private final Integer modelYear;
    private final BodyType bodyType;
    private final EngineVariant engineVariant;
    private final TransmissionVariant transmissionVariant;
    private final Integer serialNumber;
    private final Market market;
    private final Boolean isFacelift;
    private final Boolean isReventon;
    private final Boolean isSuperVeloce;

    @Override
    public int compareTo(Vin o) {
        return this.modelYear.compareTo(o.modelYear) == 0 ? 0 : this.serialNumber.compareTo(o.serialNumber);
    }
}