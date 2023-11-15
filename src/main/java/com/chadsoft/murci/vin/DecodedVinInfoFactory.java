package com.chadsoft.murci.vin;

import com.chadsoft.murci.vin.enums.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.chadsoft.murci.vin.VinConstants.*;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DecodedVinInfoFactory {

    public static Mono<DecodedVinInfo> decodeFromVin(String vin) {
        return Mono.just(vin)
                .map(VinUtils::formatVinForValidation)
                .flatMap(VinValidator::getValidVinOrError)
                .onErrorResume(Mono::error)
                .map(DecodedVinInfoFactory::buildVin);
    }

    private static DecodedVinInfo buildVin(String vin) {
        return switch (VinUtils.getVinType(vin)) {
            case MY2003 -> DecodedVinInfo.builder()
                    .fullVin(vin)
                    .vinType(VinType.MY2003)
                    .modelYear(getModelYear(vin))
                    .bodyType(BodyType.COUPE)
                    .engineVariant(EngineVariant.V12_62_580HP)
                    .transmissionVariant(TransmissionVariant.MANUAL)
                    .serialNumber(getOldSerialNumber(vin))
                    .market(getOldMarket(vin))
                    .isFacelift(false)
                    .isReventon(false)
                    .isSuperVeloce(false)
                    .build();
            case MY2009 -> DecodedVinInfo.builder()
                    .fullVin(vin)
                    .vinType(VinType.MY2009)
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
            case MY2010 -> DecodedVinInfo.builder()
                    .fullVin(vin)
                    .vinType(VinType.MY2010)
                    .modelYear(getModelYear(vin))
                    .bodyType(getBodyType(vin))
                    .engineVariant(get2010EngineVariant(vin))
                    .transmissionVariant(get2010TransmissionVariant(vin))
                    .serialNumber(getNewSerialNumber(vin))
                    .market(getNewMarket(vin))
                    .isFacelift(isFacelift(vin))
                    .isReventon(isReventon(vin))
                    .isSuperVeloce(isSuperVeloce(vin))
                    .build();
        };
    }

    private static EngineVariant get2010EngineVariant(String vin) {
        if ('L' == vin.charAt(7) || 'N' == vin.charAt(7)) {
            return EngineVariant.V12_65_640HP;
        }
        if ('G' == vin.charAt(7) || 'H' == vin.charAt(7)) {
            return EngineVariant.V12_65_670HP;
        }
        return null;
    }

    private static TransmissionVariant get2010TransmissionVariant(String vin) {
        if ('L' == vin.charAt(7) || 'G' == vin.charAt(7)) {
            return TransmissionVariant.MANUAL;
        }
        if ('N' == vin.charAt(7) || 'H' == vin.charAt(7)) {
            return TransmissionVariant.EGEAR;
        }
        return null;
    }

    private static int getModelYear(String vin) {
        if ('A' == vin.charAt(9)) {
            return 2010;
        } else {
            return 2000 + Character.getNumericValue(vin.charAt(9));
        }
    }

    private static BodyType getBodyType(String vin) {
        return MY2009_ALLOWED_BODY_CHARS.get(vin.charAt(5));
    }
    
    private static EngineVariant getEngineVariant(String vin) {
        return MY2009_ALLOWED_ENGINE_CHARS.get(vin.charAt(6));
    }

    private static TransmissionVariant getTransmissionVariant(String vin) {
        return MY2009_ALLOWED_TRANSMISSION_CHARS.get(vin.charAt(7));
    }

    private static Market getOldMarket(String vin) {
        return MY2003_ALLOWED_MARKET_CHARS.get(vin.charAt(7));
    }

    private static Market getNewMarket(String vin) {
        return MY2009_ALLOWED_MARKET_CHARS.get(vin.charAt(4));
    }

    private static Integer getOldSerialNumber(String vin) {
        return Integer.valueOf(vin.substring(14));
    }

    private static Integer getNewSerialNumber(String vin) {
        return Integer.valueOf(vin.substring(12));
    }

    private static boolean isFacelift(String vin) {
        return '3' == vin.charAt(5) || '4' == vin.charAt(5);
    }

    private static boolean isReventon(String vin) {
        return '7' == vin.charAt(5) || '9' == vin.charAt(5);
    }

    private static boolean isSuperVeloce(String vin) {
        return '8' == vin.charAt(5) ;
    }
}