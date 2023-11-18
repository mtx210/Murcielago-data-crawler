package com.chadsoft.murci.vin;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecodedVinInfoTest {

    @ParameterizedTest
    @MethodSource("compareTestArguments")
    void compareTo_tests(int expectedComparisonResult, DecodedVinInfo arg1, DecodedVinInfo arg2) {
        assertEquals(expectedComparisonResult, arg1.compareTo(arg2));
    }

    private static Stream<Arguments> compareTestArguments() {
        return Stream.of(
                Arguments.of(-1,
                        DecodedVinInfo.builder().modelYear(2002).serialNumber(100).build(),
                        DecodedVinInfo.builder().modelYear(2002).serialNumber(200).build()),
                Arguments.of(1,
                        DecodedVinInfo.builder().modelYear(2002).serialNumber(200).build(),
                        DecodedVinInfo.builder().modelYear(2002).serialNumber(100).build()),
                Arguments.of(0,
                        DecodedVinInfo.builder().modelYear(2002).serialNumber(100).build(),
                        DecodedVinInfo.builder().modelYear(2002).serialNumber(100).build()),
                Arguments.of(-1,
                        DecodedVinInfo.builder().modelYear(2002).serialNumber(100).build(),
                        DecodedVinInfo.builder().modelYear(2003).serialNumber(200).build()),
                Arguments.of(-1,
                        DecodedVinInfo.builder().modelYear(2002).serialNumber(200).build(),
                        DecodedVinInfo.builder().modelYear(2003).serialNumber(100).build()),
                Arguments.of(-1,
                        DecodedVinInfo.builder().modelYear(2002).serialNumber(100).build(),
                        DecodedVinInfo.builder().modelYear(2003).serialNumber(100).build()),
                Arguments.of(1,
                        DecodedVinInfo.builder().modelYear(2003).serialNumber(100).build(),
                        DecodedVinInfo.builder().modelYear(2002).serialNumber(200).build()),
                Arguments.of(1,
                        DecodedVinInfo.builder().modelYear(2003).serialNumber(200).build(),
                        DecodedVinInfo.builder().modelYear(2002).serialNumber(100).build()),
                Arguments.of(1,
                        DecodedVinInfo.builder().modelYear(2003).serialNumber(100).build(),
                        DecodedVinInfo.builder().modelYear(2002).serialNumber(100).build())
        );
    }
}