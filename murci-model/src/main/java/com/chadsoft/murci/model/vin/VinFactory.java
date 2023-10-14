package com.chadsoft.murci.model.vin;

import com.chadsoft.murci.model.vin.enums.BodyType;
import com.chadsoft.murci.model.vin.enums.EngineVariant;
import com.chadsoft.murci.model.vin.enums.Market;
import com.chadsoft.murci.model.vin.enums.TransmissionVariant;
import reactor.core.publisher.Mono;

import static com.chadsoft.murci.model.vin.VinConstants.*;
import static com.chadsoft.murci.model.vin.VinUtils.formatVinForValidation;

public class VinFactory {

    public static Mono<Vin> createFromVin(String vin) {

        return Mono.just(vin)
                .map(VinUtils::formatVinForValidation)
                .filter(VinValidator::isVinValid)
                .switchIfEmpty(Mono.empty())
                .map(VinFactory::buildVin);


        vin = formatVinForValidation(vin);
        if (VinValidator.isVinValid(vin)) {
            if (VinUtils.isOldTypeVin(vin)) {
                return Vin.builder()
                        .fullVin(vin)
                        .isOldStandard(true)
                        .modelYear(getModelYear(vin)) 
                        .bodyType(BodyType.COUPE) //TODO
                        .engineVariant(EngineVariant.V12_62)
                        .transmissionVariant(TransmissionVariant.MANUAL) //TODO
                        .serialNumber(getOldSerialNumber(vin)) 
                        .market(getOldMarket(vin))
                        .isFacelift(false)
                        .isReventon(false)
                        .isSuperVeloce(false)
                        .build();
            } else {
                return Vin.builder()
                        .fullVin(vin)
                        .isOldStandard(false)
                        .modelYear(getModelYear(vin)) 
                        .bodyType(getBodyType(vin)) 
                        .engineVariant(getEngineVariant(vin)) 
                        .transmissionVariant(getTransmissionVariant(vin)) 
                        .serialNumber(getNewSerialNumber(vin)) 
                        .market(getNewMarket(vin))
                        .isFacelift(isFacelift(vin))
                        .isReventon(isReventon(vin))
                        .isSuperVeloce(isSuperVeloce(vin))
                        .build();
            }
        } else {
            return Vin.builder().build();
        }
    }

    private static Vin buildVin(String vin) {
        if (VinUtils.isOldTypeVin(vin)) {
            return Vin.builder()
                    .fullVin(vin)
                    .isOldStandard(true)
                    .modelYear(getModelYear(vin))
                    .bodyType(BodyType.COUPE) //TODO
                    .engineVariant(EngineVariant.V12_62)
                    .transmissionVariant(TransmissionVariant.MANUAL) //TODO
                    .serialNumber(getOldSerialNumber(vin))
                    .market(getOldMarket(vin))
                    .isFacelift(false)
                    .isReventon(false)
                    .isSuperVeloce(false)
                    .build();
        } else {
            return Vin.builder()
                    .fullVin(vin)
                    .isOldStandard(false)
                    .modelYear(getModelYear(vin))
                    .bodyType(getBodyType(vin))
                    .engineVariant(getEngineVariant(vin))
                    .transmissionVariant(getTransmissionVariant(vin))
                    .serialNumber(getNewSerialNumber(vin))
                    .market(getNewMarket(vin))
                    .isFacelift(isFacelift(vin))
                    .isReventon(isReventon(vin))
                    .isSuperVeloce(isSuperVeloce(vin))
                    .build();
        }
    }

    private static int getModelYear(String vin) {
        if ('A' == vin.charAt(9)) {
            return 2010;
        } else {
            return 2000 + Character.getNumericValue(vin.charAt(9));
        }
    }

    private static BodyType getBodyType(String vin) {
        return ALLOWED_BODY_NEW_CHARS.get(vin.charAt(5));
    }
    
    private static EngineVariant getEngineVariant(String vin) {
        return ALLOWED_ENGINE_CHARS.get(vin.charAt(6));
    }

    private static TransmissionVariant getTransmissionVariant(String vin) {
        return ALLOWED_TRANSMISSION_CHARS.get(vin.charAt(7));
    }

    private static Market getOldMarket(String vin) {
        return ALLOWED_OLD_MARKET_CHARS.get(vin.charAt(7));
    }

    private static Market getNewMarket(String vin) {
        return ALLOWED_NEW_MARKET_CHARS.get(vin.charAt(4));
    }

    private static Integer getOldSerialNumber(String vin) {
        return Integer.valueOf(vin.substring(14));
    }

    private static Integer getNewSerialNumber(String vin) {
        return Integer.valueOf(vin.substring(12));
    }

    private static boolean isFacelift(String vin) {
        return '3' == vin.charAt(5) && '4' == vin.charAt(5);
    }

    private static boolean isReventon(String vin) {
        return '7' == vin.charAt(5) && '9' == vin.charAt(5);
    }

    private static boolean isSuperVeloce(String vin) {
        return '8' == vin.charAt(5) ;
    }
}