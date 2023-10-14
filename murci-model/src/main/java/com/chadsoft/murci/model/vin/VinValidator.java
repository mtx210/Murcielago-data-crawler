package com.chadsoft.murci.model.vin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Predicate;

import static com.chadsoft.murci.model.vin.VinConstants.*;
import static com.chadsoft.murci.model.vin.VinUtils.isOldTypeVin;

class VinValidator {

    static boolean isVinValid(String vin) {
        boolean xd = isOldTypeVin(vin) ? validateOldVin(vin) : validateNewVin(vin);
        if (!xd) {
            System.out.println(vin);
        }
        return xd;
    }

    private static Predicate<String> validateCommonVinParams() {
        return CommonVinValidators.LENGTH.getValidator()
                .and(CommonVinValidators.FORBIDDEN_VIN_CHARS.getValidator())
                .and(CommonVinValidators.REGION.getValidator())
                .and(CommonVinValidators.MODEL.getValidator())
                .and(CommonVinValidators.CHECK_DIGIT.getValidator())
                .and(CommonVinValidators.MODEL_YEAR.getValidator())
                .and(CommonVinValidators.PLANT.getValidator())
                .and(CommonVinValidators.SERIAL_NUMBER.getValidator());
    }

    private static boolean validateOldVin(String vin) {
        return validateCommonVinParams()
                .and(OldVinValidators.COUNTRY.getValidator())
                .and(OldVinValidators.MANUFACTURER.getValidator())
                .and(OldVinValidators.BODY.getValidator())
                .and(OldVinValidators.ENGINE.getValidator())
                .and(OldVinValidators.MARKET.getValidator())
                .test(vin);
    }

    private static boolean validateNewVin(String vin) {
        return validateCommonVinParams()
                .and(NewVinValidators.COUNTRY.getValidator())
                .and(NewVinValidators.MANUFACTURER.getValidator())
                .and(NewVinValidators.MARKET.getValidator())
                .and(NewVinValidators.BODY.getValidator())
                .and(NewVinValidators.ENGINE.getValidator())
                .and(NewVinValidators.TRANSMISSION.getValidator())
                .and(NewVinValidators.SERIAL_NUMBER_PREFIX.getValidator())
                .and(NewVinValidators.SERIAL_NUMBER.getValidator())
                .test(vin);
    }

    @RequiredArgsConstructor
    @Getter
    private enum CommonVinValidators {
        LENGTH(vin -> vin.length() == VIN_LENGTH),
        FORBIDDEN_VIN_CHARS(vin -> !vin.contains("I") && !vin.contains("O") && !vin.contains("Q")),
        REGION(vin -> REGION_EUROPE == vin.charAt(0)),
        MODEL(vin -> MODEL_MURCIELAGO == vin.charAt(3)),
        CHECK_DIGIT(vin -> ALLOWED_CHECK_DIGITS_CHARS.contains(vin.charAt(8))),
        MODEL_YEAR(vin -> ALLOWED_MODEL_YEAR_DIGITS_CHARS.contains(vin.charAt(9))),
        PLANT(vin -> PLANT_BOLOGNA == vin.charAt(10)),
        SERIAL_NUMBER(vin -> StringUtils.isNumeric(vin.substring(14)));

        private final Predicate<String> validator;
    }

    @RequiredArgsConstructor
    @Getter
    private enum OldVinValidators {
        COUNTRY(vin -> COUNTRY_ITALY_OLD == vin.charAt(1)),
        MANUFACTURER(vin -> MANUFACTURER_OLD == vin.charAt(2) && MANUFACTURER_OLD_SUFFIX.equals(vin.substring(11,14))),
        BODY(vin -> BODY_OLD == vin.charAt(4)),
        ENGINE(vin -> ENGINE_62.equals(vin.substring(5,7))),
        MARKET(vin -> ALLOWED_OLD_MARKET_CHARS.containsKey(vin.charAt(7)));

        private final Predicate<String> validator;
    }

    @RequiredArgsConstructor
    @Getter
    private enum NewVinValidators {
        COUNTRY(vin -> COUNTRY_ITALY_NEW == vin.charAt(1)),
        MANUFACTURER(vin -> MANUFACTURER_NEW == vin.charAt(2)),
        MARKET(vin -> ALLOWED_NEW_MARKET_CHARS.containsKey(vin.charAt(4))),
        BODY(vin -> ALLOWED_BODY_NEW_CHARS.containsKey(vin.charAt(5))),
        ENGINE(vin -> ALLOWED_ENGINE_CHARS.containsKey(vin.charAt(6))),
        TRANSMISSION(vin -> ALLOWED_TRANSMISSION_CHARS.containsKey(vin.charAt(7))),
        SERIAL_NUMBER_PREFIX(vin -> VinConstants.SERIAL_NUMBER_PREFIX == vin.charAt(11)),
        SERIAL_NUMBER(vin -> StringUtils.isNumeric(vin.substring(12)));

        private final Predicate<String> validator;
    }
}
