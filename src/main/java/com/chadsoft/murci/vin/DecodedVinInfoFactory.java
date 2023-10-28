package com.chadsoft.murci.vin;

import com.chadsoft.murci.vin.enums.BodyType;
import com.chadsoft.murci.vin.enums.EngineVariant;
import com.chadsoft.murci.vin.enums.Market;
import com.chadsoft.murci.vin.enums.TransmissionVariant;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.chadsoft.murci.vin.VinConstants.*;


@Component
public class DecodedVinInfoFactory {

    private DecodedVinInfoFactory() {}

    public static Mono<DecodedVinInfo> decodeFromVin(String vin) {
        return Mono.just(vin)
                .map(VinUtils::formatVinForValidation)
                .flatMap(VinValidator::getValidVinOrError)
                .onErrorResume(Mono::error)
                .map(DecodedVinInfoFactory::buildVin);
    }

    private static DecodedVinInfo buildVin(String vin) {
        if (VinUtils.isOldTypeVin(vin)) {
            return DecodedVinInfo.builder()
                    .fullVin(vin)
                    .isOldStandard(true)
                    .modelYear(getModelYear(vin))
                    .bodyType(BodyType.COUPE)
                    .engineVariant(EngineVariant.V12_62)
                    .transmissionVariant(TransmissionVariant.MANUAL)
                    .serialNumber(getOldSerialNumber(vin))
                    .market(getOldMarket(vin))
                    .isFacelift(false)
                    .isReventon(false)
                    .isSuperVeloce(false)
                    .build();
        } else {
            return DecodedVinInfo.builder()
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