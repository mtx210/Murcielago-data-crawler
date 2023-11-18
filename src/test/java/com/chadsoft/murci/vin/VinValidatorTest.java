package com.chadsoft.murci.vin;

import com.chadsoft.murci.vin.exception.VinValidationException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

@SpringBootTest
class VinValidatorTest {

    @ParameterizedTest/*(name = "{index} {0}")*/
    @MethodSource("getValidVinOrError_successArgs")
    void getValidVinOrError_shouldSucceed(String vin) {
        StepVerifier.create(VinValidator.getValidVinOrError(vin))
                .expectNext(vin)
                .verifyComplete();
    }

    private static Stream<Arguments> getValidVinOrError_successArgs() {
        return Stream.of(
                Arguments.of("ZA9BC10E02LA12047"),
                Arguments.of("ZA9BC10U02LA12047"),
                Arguments.of("ZA9BC10C02LA12047"),

                Arguments.of("ZHWBE37M17LA02317"),
                Arguments.of("ZHWBU37M17LA02317"),
                Arguments.of("ZHWBC37M17LA02317"),
                Arguments.of("ZHWBA37M17LA02317"),
                Arguments.of("ZHWBU17M17LA02317"),
                Arguments.of("ZHWBU27M17LA02317"),
                Arguments.of("ZHWBU37M17LA02317"),
                Arguments.of("ZHWBU47M17LA02317"),
                Arguments.of("ZHWBU77M17LA02317"),
                Arguments.of("ZHWBU87M17LA02317"),
                Arguments.of("ZHWBU97M17LA02317"),
                Arguments.of("ZHWBU36M17LA02317"),
                Arguments.of("ZHWBU37M17LA02317"),
                Arguments.of("ZHWBU38M17LA02317"),
                Arguments.of("ZHWBU37M17LA02317"),
                Arguments.of("ZHWBU37N17LA02317"),
                Arguments.of("ZHWBU37S17LA02317"),
                Arguments.of("ZHWBU37T17LA02317"),

                Arguments.of("ZHWBE8AGXALA03957"),
                Arguments.of("ZHWBU8AGXALA03957"),
                Arguments.of("ZHWBC8AGXALA03957"),
                Arguments.of("ZHWBA8AGXALA03957"),
                Arguments.of("ZHWBU1AGXALA03957"),
                Arguments.of("ZHWBU2AGXALA03957"),
                Arguments.of("ZHWBU3AGXALA03957"),
                Arguments.of("ZHWBU4AGXALA03957"),
                Arguments.of("ZHWBU7AGXALA03957"),
                Arguments.of("ZHWBU8AGXALA03957"),
                Arguments.of("ZHWBU9AGXALA03957"),
                Arguments.of("ZHWBU8AGXALA03957"),
                Arguments.of("ZHWBU81GXALA03957"),
                Arguments.of("ZHWBU8ALXALA03957"),
                Arguments.of("ZHWBU8ANXALA03957"),
                Arguments.of("ZHWBU8AGXALA03957"),
                Arguments.of("ZHWBU8AHXALA03957")
        );
    }

    @ParameterizedTest
    @MethodSource("getValidVinOrError_failArgs")
    void getValidVinOrError_shouldFail(String vin, String expectedFailReason) {
        StepVerifier.create(VinValidator.getValidVinOrError(vin))
                .expectErrorMatches(throwable -> assertThrowable(throwable, expectedFailReason))
                .verify();
    }

    private boolean assertThrowable(Throwable throwable, String expectedFailReason) {
        return throwable instanceof VinValidationException &&
                ((VinValidationException) throwable).getValidationFailReason().equals(expectedFailReason);
    }

    private static Stream<Arguments> getValidVinOrError_failArgs() {
        return Stream.of(
                Arguments.of("ZA9BC10C52LA1229", "VIN length not equal 17"),
                Arguments.of("ZA9BCI0C52LA12297", "VIN contains forbidden characters"),
                Arguments.of("ZA9BC1OC52LA12297", "VIN contains forbidden characters"),
                Arguments.of("ZA9BC1QC52LA12297", "VIN contains forbidden characters"),
                Arguments.of("2A9BC10C52LA12297", "VIN contains invalid region character"),
                Arguments.of("ZA98C10C52LA12297", "VIN contains invalid model character"),
                Arguments.of("ZA9BC10CA2LA12297", "VIN contains invalid check digit character"),
                Arguments.of("ZA9BC10C5BLA12297", "VIN contains invalid model year character"),
                Arguments.of("ZA9BC10C52KA12297", "VIN contains invalid plant character"),
                Arguments.of("ZA9BC10C52LA12A97", "VIN serial number not numeric"),

                Arguments.of("ZB9BC10E02LA12054", "MY2009 type VIN contains invalid country character"),
                Arguments.of("ZA8BC10E02LA12054", "MY2009 type VIN contains invalid country character"),
                Arguments.of("ZA9BC10E02LA13054", "MY2003 type VIN contains invalid manufacturer info"),
                Arguments.of("ZA8BC10E02LA13054", "MY2009 type VIN contains invalid country character"),
                Arguments.of("ZA9BE10E02LA12054", "MY2003 type VIN contains invalid body character"),
                Arguments.of("ZA9BC20E02LA12054", "MY2003 type VIN contains invalid engine code"),
                Arguments.of("ZA9BC10Z02LA12054", "MY2003 type VIN contains invalid market character"),

                Arguments.of("ZMWBU47M49LA03406", "MY2009 type VIN contains invalid country character"),
                Arguments.of("ZHMBU47M49LA03406", "MY2009 type VIN contains invalid manufacturer character"),
                Arguments.of("ZHWBB47M49LA03406", "MY2009 type VIN contains invalid market character"),
                Arguments.of("ZHWBU57M49LA03406", "MY2009 type VIN contains invalid body character"),
                Arguments.of("ZHWBU49M49LA03406", "MY2009 type VIN contains invalid engine character"),
                Arguments.of("ZHWBU47K49LA03406", "MY2009 type VIN contains invalid transmission character"),
                Arguments.of("ZHWBU47M49LE03406", "MY2009 type VIN contains invalid serial number prefix"),
                Arguments.of("ZHWBU47M49LAA3406", "MY2009 type VIN serial number not numeric"),

                Arguments.of("ZMWBU8AH8ALA03707", "MY2009 type VIN contains invalid country character"),
                Arguments.of("ZHMBU8AH8ALA03707", "MY2009 type VIN contains invalid manufacturer character"),
                Arguments.of("ZHWBB8AH8ALA03707", "MY2009 type VIN contains invalid market character"),
                Arguments.of("ZHWBU6AH8ALA03707", "MY2009 type VIN contains invalid body character"),
                Arguments.of("ZHWBU83H8ALA03707", "MY2010 type VIN contains invalid drivetrain character"),
                Arguments.of("ZHWBU8AM8ALA03707", "MY2010 type VIN contains invalid transmission character"),
                Arguments.of("ZHWBU8AH8ALE03707", "MY2009 type VIN contains invalid serial number prefix"),
                Arguments.of("ZHWBU8AH8ALAA3707", "MY2009 type VIN serial number not numeric")
        );
    }
}