package com.chadsoft.murci.vin;

import com.chadsoft.murci.vin.enums.VinType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class VinUtilsTest {

    @Test
    void formatVinForValidation() {
        assertEquals("ABC123DEF456", VinUtils.formatVinForValidation("    abc123DEF456   "));
    }

    @ParameterizedTest
    @MethodSource("getVinTypeTestArgs")
    void getVinType(String vin, VinType expectedResult) {
        assertEquals(expectedResult, VinUtils.getVinType(vin));
    }

    private static Stream<Arguments> getVinTypeTestArgs() {
        return Stream.of(
                Arguments.of("ZHWBU8AH2ALA03704", VinType.MY2010),
                Arguments.of("ZHWBE41N3ALA04064", VinType.MY2010),
                Arguments.of("ZHWBU37M17LA02334", VinType.MY2009),
                Arguments.of("ZHWBU47S58LA02939", VinType.MY2009),
                Arguments.of("ZA9BC10C32LA12069", VinType.MY2003),
                Arguments.of("ZA9BC10U13LA12517", VinType.MY2003)
        );
    }
}