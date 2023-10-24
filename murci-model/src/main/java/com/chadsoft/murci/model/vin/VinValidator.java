package com.chadsoft.murci.model.vin;

import com.chadsoft.murci.model.vin.exception.VinValidationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static com.chadsoft.murci.model.vin.VinConstants.*;

class VinValidator {

    static Mono<String> getValidVinOrError(String vin) {
        return Flux.fromIterable(getVinValidationRules(vin))
                .flatMap(rule -> rule.validateVin(vin))
                .then(Mono.just(vin));
    }

    private static List<VinValidationRule> getVinValidationRules(String vin) {
        List<VinValidationRule> vinValidationRules = new ArrayList<>();

        vinValidationRules.addAll(getCommonValidationRules());
        vinValidationRules.addAll(getOldOrNewValidationRules(vin));

        return vinValidationRules;
    }

    private static List<VinValidationRule> getCommonValidationRules() {
        return Arrays.stream(CommonVinValidators.values())
                .map(CommonVinValidators::getValidationFunction)
                .toList();
    }

    private static List<VinValidationRule> getOldOrNewValidationRules(String vin) {
        return VinUtils.isOldTypeVin(vin) ?
                Arrays.stream(OldVinValidators.values())
                        .map(OldVinValidators::getValidationFunction)
                        .toList() :
                Arrays.stream(NewVinValidators.values())
                        .map(NewVinValidators::getValidationFunction)
                        .toList();
    }

    @FunctionalInterface
    private interface VinValidationRule {
        Mono<String> validateVin(String vin);
    }

    @RequiredArgsConstructor
    @Getter
    private enum CommonVinValidators {
        LENGTH(
                vin -> vin.length() == VIN_LENGTH,
                "VIN length not equal 17"),
        FORBIDDEN_VIN_CHARS(
                vin -> !vin.contains("I") && !vin.contains("O") && !vin.contains("Q"),
                "VIN contains forbidden characters"),
        REGION(
                vin -> REGION_EUROPE == vin.charAt(0),
                "VIN contains invalid region character"),
        MODEL(
                vin -> MODEL_MURCIELAGO == vin.charAt(3),
                "VIN contains invalid model character"),
        CHECK_DIGIT(
                vin -> ALLOWED_CHECK_DIGITS_CHARS.contains(vin.charAt(8)),
                "VIN contains invalid check digit character"),
        MODEL_YEAR(
                vin -> ALLOWED_MODEL_YEAR_DIGITS_CHARS.contains(vin.charAt(9)),
                "VIN contains invalid model year character"),
        PLANT(
                vin -> PLANT_BOLOGNA == vin.charAt(10),
                "VIN contains invalid plant character"),
        SERIAL_NUMBER(
                vin -> StringUtils.isNumeric(vin.substring(14)),
                "VIN serial number not numeric");

        private VinValidationRule validationFunction;

        CommonVinValidators(Predicate<String> validationFilter, String failMessage) {
            this.validationFunction = vin -> Mono.just(vin)
                    .filter(validationFilter)
                    .switchIfEmpty(Mono.error(new VinValidationException(vin, failMessage)));
        }
    }

    @RequiredArgsConstructor
    @Getter
    private enum OldVinValidators {
        COUNTRY(
                vin -> COUNTRY_ITALY_OLD == vin.charAt(1),
                "Old type VIN contains invalid country character"),
        MANUFACTURER(
                vin -> MANUFACTURER_OLD == vin.charAt(2) && MANUFACTURER_OLD_SUFFIX.equals(vin.substring(11, 14)),
                "Old type VIN contains invalid manufacturer info"),
        BODY(
                vin -> BODY_OLD == vin.charAt(4),
                "Old type VIN contains invalid body character"),
        ENGINE(
                vin -> ENGINE_62.equals(vin.substring(5, 7)),
                "Old type VIN contains invalid engine code"),
        MARKET(
                vin -> ALLOWED_OLD_MARKET_CHARS.containsKey(vin.charAt(7)),
                "Old type VIN contains invalid market character");

        private VinValidationRule validationFunction;

        OldVinValidators(Predicate<String> validationFilter, String failMessage) {
            this.validationFunction = vin -> Mono.just(vin)
                    .filter(validationFilter)
                    .switchIfEmpty(Mono.error(new VinValidationException(vin, failMessage)));
        }
    }

    @RequiredArgsConstructor
    @Getter
    private enum NewVinValidators {
        COUNTRY(
                vin -> COUNTRY_ITALY_NEW == vin.charAt(1),
                "New type VIN contains invalid country character"),
        MANUFACTURER(
                vin -> MANUFACTURER_NEW == vin.charAt(2),
                "New type VIN contains invalid manufacturer character"),
        MARKET(
                vin -> ALLOWED_NEW_MARKET_CHARS.containsKey(vin.charAt(4)),
                "New type VIN contains invalid market character"),
        BODY(
                vin -> ALLOWED_BODY_NEW_CHARS.containsKey(vin.charAt(5)),
                "New type VIN contains invalid body character"),
        ENGINE(
                vin -> ALLOWED_ENGINE_CHARS.containsKey(vin.charAt(6)),
                "New type VIN contains invalid engine character"),
        TRANSMISSION(
                vin -> ALLOWED_TRANSMISSION_CHARS.containsKey(vin.charAt(7)),
                "New type VIN contains invalid transmission character"),
        SERIAL_NUMBER_PREFIX(
                vin -> VinConstants.SERIAL_NUMBER_PREFIX == vin.charAt(11),
                "New type VIN contains invalid serial number prefix"),
        SERIAL_NUMBER(
                vin -> StringUtils.isNumeric(vin.substring(12)),
                "New type VIN serial number not numeric");

        private VinValidationRule validationFunction;

        NewVinValidators(Predicate<String> validationFilter, String failMessage) {
            this.validationFunction = vin -> Mono.just(vin)
                    .filter(validationFilter)
                    .switchIfEmpty(Mono.error(new VinValidationException(vin, failMessage)));
        }
    }
}
