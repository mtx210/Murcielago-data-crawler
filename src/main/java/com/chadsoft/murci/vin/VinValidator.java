package com.chadsoft.murci.vin;

import com.chadsoft.murci.vin.exception.VinValidationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static com.chadsoft.murci.vin.VinConstants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class VinValidator {

    @FunctionalInterface
    private interface VinValidationRule {
        Mono<String> validateVin(String vin);
    }

    static Mono<String> getValidVinOrError(String vin) {
        return Flux.fromIterable(getVinValidationRules(vin))
                .flatMap(rule -> rule.validateVin(vin))
                .onErrorResume(Mono::error)
                .then(Mono.just(vin));
    }

    private static List<VinValidationRule> getVinValidationRules(String vin) {
        List<VinValidationRule> vinValidationRules = new ArrayList<>();

        vinValidationRules.addAll(getCommonValidationRules());
        vinValidationRules.addAll(getVinTypeSpecificValidationRules(vin));

        return vinValidationRules;
    }

    private static List<VinValidationRule> getCommonValidationRules() {
        return Arrays.stream(CommonVinValidators.values())
                .map(CommonVinValidators::getValidationFunction)
                .toList();
    }

    private static List<VinValidationRule> getVinTypeSpecificValidationRules(String vin) {
        return switch (VinUtils.getVinType(vin)) {
            case MY2003 -> Arrays.stream(ModelYear2003VinValidators.values())
                    .map(ModelYear2003VinValidators::getValidationFunction)
                    .toList();
            case MY2009 -> Arrays.stream(ModelYear2009VinValidators.values())
                    .map(ModelYear2009VinValidators::getValidationFunction)
                    .toList();
            case MY2010 -> Arrays.stream(ModelYear2010VinValidators.values())
                    .map(ModelYear2010VinValidators::getValidationFunction)
                    .toList();
        };
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
    private enum ModelYear2003VinValidators {
        COUNTRY(
                vin -> MY2003_COUNTRY_ITALY == vin.charAt(1),
                "Old type VIN contains invalid country character"),
        MANUFACTURER(
                vin -> MY2003_MANUFACTURER_LAMBORGHINI == vin.charAt(2) && MY2003_MANUFACTURER_SUFFIX_LAMBORGHINI.equals(vin.substring(11, 14)),
                "Old type VIN contains invalid manufacturer info"),
        BODY(
                vin -> MY2003_BODY == vin.charAt(4),
                "Old type VIN contains invalid body character"),
        ENGINE(
                vin -> MY2003_ENGINE_62.equals(vin.substring(5, 7)),
                "Old type VIN contains invalid engine code"),
        MARKET(
                vin -> MY2003_ALLOWED_MARKET_CHARS.containsKey(vin.charAt(7)),
                "Old type VIN contains invalid market character");

        private VinValidationRule validationFunction;

        ModelYear2003VinValidators(Predicate<String> validationFilter, String failMessage) {
            this.validationFunction = vin -> Mono.just(vin)
                    .filter(validationFilter)
                    .switchIfEmpty(Mono.error(new VinValidationException(vin, failMessage)));
        }
    }

    @RequiredArgsConstructor
    @Getter
    private enum ModelYear2009VinValidators {
        COUNTRY(
                vin -> MY2009_COUNTRY_ITALY == vin.charAt(1),
                "New type VIN contains invalid country character"),
        MANUFACTURER(
                vin -> MY2009_MANUFACTURER_LAMBORGHINI == vin.charAt(2),
                "New type VIN contains invalid manufacturer character"),
        MARKET(
                vin -> MY2009_ALLOWED_MARKET_CHARS.containsKey(vin.charAt(4)),
                "New type VIN contains invalid market character"),
        BODY(
                vin -> MY2009_ALLOWED_BODY_CHARS.containsKey(vin.charAt(5)),
                "New type VIN contains invalid body character"),
        ENGINE(
                vin -> MY2009_ALLOWED_ENGINE_CHARS.containsKey(vin.charAt(6)),
                "New type VIN contains invalid engine character"),
        TRANSMISSION(
                vin -> MY2009_ALLOWED_TRANSMISSION_CHARS.containsKey(vin.charAt(7)),
                "New type VIN contains invalid transmission character"),
        SERIAL_NUMBER_PREFIX(
                vin -> VinConstants.MY2009_SERIAL_NUMBER_PREFIX == vin.charAt(11),
                "New type VIN contains invalid serial number prefix"),
        SERIAL_NUMBER(
                vin -> StringUtils.isNumeric(vin.substring(12)),
                "New type VIN serial number not numeric");

        private VinValidationRule validationFunction;

        ModelYear2009VinValidators(Predicate<String> validationFilter, String failMessage) {
            this.validationFunction = vin -> Mono.just(vin)
                    .filter(validationFilter)
                    .switchIfEmpty(Mono.error(new VinValidationException(vin, failMessage)));
        }
    }

    @RequiredArgsConstructor
    @Getter
    private enum ModelYear2010VinValidators {
        COUNTRY(
                vin -> MY2009_COUNTRY_ITALY == vin.charAt(1),
                "New type VIN contains invalid country character"),
        MANUFACTURER(
                vin -> MY2009_MANUFACTURER_LAMBORGHINI == vin.charAt(2),
                "New type VIN contains invalid manufacturer character"),
        MARKET(
                vin -> MY2009_ALLOWED_MARKET_CHARS.containsKey(vin.charAt(4)),
                "New type VIN contains invalid market character"),
        BODY(
                vin -> MY2009_ALLOWED_BODY_CHARS.containsKey(vin.charAt(5)),
                "New type VIN contains invalid body character"),
        ENGINE(
                vin -> MY2010_ALLOWED_DRIVETRAIN_CHARS.contains(vin.charAt(6)),
                "Newest type VIN contains invalid drivetrain character"),
        TRANSMISSION(
                vin -> MY2010_ALLOWED_TRANSMISSION_CHARS.containsKey(vin.charAt(7)),
                "Newest type VIN contains invalid transmission character"),
        SERIAL_NUMBER_PREFIX(
                vin -> VinConstants.MY2009_SERIAL_NUMBER_PREFIX == vin.charAt(11),
                "New type VIN contains invalid serial number prefix"),
        SERIAL_NUMBER(
                vin -> StringUtils.isNumeric(vin.substring(12)),
                "New type VIN serial number not numeric");

        private VinValidationRule validationFunction;

        ModelYear2010VinValidators(Predicate<String> validationFilter, String failMessage) {
            this.validationFunction = vin -> Mono.just(vin)
                    .filter(validationFilter)
                    .switchIfEmpty(Mono.error(new VinValidationException(vin, failMessage)));
        }
    }
}
