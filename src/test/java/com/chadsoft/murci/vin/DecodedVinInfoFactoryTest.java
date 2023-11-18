package com.chadsoft.murci.vin;

import com.chadsoft.murci.vin.enums.*;
import com.chadsoft.murci.vin.exception.VinValidationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

class DecodedVinInfoFactoryTest {

    @ParameterizedTest
    @MethodSource("decodeFromVin_shouldSucceedTestArgs")
    void decodeFromVin_shouldSucceed(String vin, DecodedVinInfo expected) {
        StepVerifier.create(DecodedVinInfoFactory.decodeFromVin(vin))
                .expectNext(expected)
                .verifyComplete();
    }

    private static Stream<Arguments> decodeFromVin_shouldSucceedTestArgs() {
        return Stream.of(
                Arguments.of("za9BC10E02La12047", DecodedVinInfo.builder()
                        .fullVin("ZA9BC10E02LA12047")
                        .vinType(VinType.MY2003)
                        .modelYear(2002)
                        .bodyType(BodyType.COUPE)
                        .engineVariant(EngineVariant.V12_62_580HP)
                        .transmissionVariant(TransmissionVariant.MANUAL)
                        .serialNumber(47)
                        .market(Market.EUROPE)
                        .isFacelift(false)
                        .isReventon(false)
                        .isSuperVeloce(false)
                        .build()
                ),
                Arguments.of("zhwbU37M17la02317", DecodedVinInfo.builder()
                        .fullVin("ZHWBU37M17LA02317")
                        .vinType(VinType.MY2009)
                        .modelYear(2007)
                        .bodyType(BodyType.COUPE)
                        .engineVariant(EngineVariant.V12_65_640HP)
                        .transmissionVariant(TransmissionVariant.MANUAL)
                        .serialNumber(2317)
                        .market(Market.UNITED_STATES)
                        .isFacelift(true)
                        .isReventon(false)
                        .isSuperVeloce(false)
                        .build()
                ),
                Arguments.of("zhwbu8AGXAla03957", DecodedVinInfo.builder()
                        .fullVin("ZHWBU8AGXALA03957")
                        .vinType(VinType.MY2010)
                        .modelYear(2010)
                        .bodyType(BodyType.COUPE)
                        .engineVariant(EngineVariant.V12_65_670HP)
                        .transmissionVariant(TransmissionVariant.MANUAL)
                        .serialNumber(3957)
                        .market(Market.UNITED_STATES)
                        .isFacelift(false)
                        .isReventon(false)
                        .isSuperVeloce(true)
                        .build()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("decodeFromVin_shouldFailTestArgs")
    void decodeFromVin_shouldFail(String vin, String expectedFailMessage) {
        StepVerifier.create(DecodedVinInfoFactory.decodeFromVin(vin))
                .expectErrorMatches(throwable -> assertThrowable(throwable, expectedFailMessage))
                .verify();
    }

    private boolean assertThrowable(Throwable throwable, String expectedFailMessage) {
        return throwable instanceof VinValidationException &&
                ((VinValidationException) throwable).getValidationFailReason().equals(expectedFailMessage);
    }

    private static Stream<Arguments> decodeFromVin_shouldFailTestArgs() {
        return Stream.of(
                Arguments.of("2A9BC10C52LA12297", "VIN contains invalid region character"),
                Arguments.of("ZA9BE10E02LA12054", "MY2003 type VIN contains invalid body character"),
                Arguments.of("ZHMBU47M49LA03406", "MY2009 type VIN contains invalid manufacturer character"),
                Arguments.of("ZHWBU83H8ALA03707", "MY2010 type VIN contains invalid drivetrain character")
        );
    }
}