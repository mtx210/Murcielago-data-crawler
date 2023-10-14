package com.chadsoft.murci.model.vin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VinValidatorTest {

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("isVinValidSuccessTestArguments")
    void isVinValid_success(String testName, String vin) {
        assertTrue(VinValidator.isVinValid(vin));
    }

    private static Stream<Arguments> isVinValidSuccessTestArguments() {
        return Stream.of(
                Arguments.of("Old VIN format test", "ZA9BC10E03LA12566"),
                Arguments.of("New VIN format test", "ZHWBU37S58LA02708")
        );
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("isVinValidFailTestArguments")
    void isVinValid_fail(String testName, String vin) {
        assertFalse(VinValidator.isVinValid(vin));
    }

    private static Stream<Arguments> isVinValidFailTestArguments() {
        return Stream.of(
                Arguments.of(
                        "Vin length test",
                        "ZA9BC10E03LA12566"),
                Arguments.of(
                        "New VIN format test",
                        "ZHWBU37S58LA02708")
        );
    }

//    @Test
//    void foooooooooooo() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        VinWikiResponse response = mapper.readValue(new File("src/test/resources/files/response.json"), VinWikiResponse.class);
//
//        List<String> sortedVins = response.getVehicles().stream()
//                .map(vehicle -> VinFactory.createFromVin(vehicle.getVin()))
//                .filter(vin -> StringUtils.isNotEmpty(vin.getFullVin()))
//                .sorted()
//                .map(Vin::getFullVin)
//                .toList();
//
//        mapper.writeValue(new File("src/test/resources/files/sorted_vins.json"), sortedVins);
//    }
}