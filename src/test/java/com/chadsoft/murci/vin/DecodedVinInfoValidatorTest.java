package com.chadsoft.murci.vin;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
class DecodedVinInfoValidatorTest {

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("isVinValidSuccessTestArguments")
    void isVinValid_success(String testName, String vin) {
        //assertTrue(VinValidator.getValidVinOrError(vin));
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
        //assertFalse(VinValidator.getValidVinOrError(vin));
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
}