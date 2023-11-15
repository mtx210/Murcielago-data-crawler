package com.chadsoft.murci.vin;

import com.chadsoft.murci.vin.enums.BodyType;
import com.chadsoft.murci.vin.enums.EngineVariant;
import com.chadsoft.murci.vin.enums.Market;
import com.chadsoft.murci.vin.enums.TransmissionVariant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class VinConstants {

    //
    // Common
    //
    static final int VIN_LENGTH = 17;
    static final Character REGION_EUROPE = 'Z';
    static final Character MODEL_MURCIELAGO = 'B';
    static final Character PLANT_BOLOGNA = 'L';
    static final List<Character> ALLOWED_CHECK_DIGITS_CHARS = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'X');
    static final List<Character> ALLOWED_MODEL_YEAR_DIGITS_CHARS = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A');

    //
    // Model year 2003
    //
    static final Character MY2003_COUNTRY_ITALY = 'A';
    static final String MY2003_VIN_PREFIX = "ZA9";
    static final Character MY2003_MANUFACTURER_LAMBORGHINI = '9';
    static final String MY2003_MANUFACTURER_SUFFIX_LAMBORGHINI = "A12";
    static final Character MY2003_BODY = 'C';
    static final String MY2003_ENGINE_62 = "10";
    static final Map<Character, Market> MY2003_ALLOWED_MARKET_CHARS = Map.of(
            'E', Market.EUROPE,
            'U', Market.UNITED_STATES,
            'C', Market.CANADA
    );

    //
    // Model year 2009
    //
    static final Character MY2009_SERIAL_NUMBER_PREFIX = 'A';
    static final Character MY2009_COUNTRY_ITALY = 'H';
    static final Character MY2009_MANUFACTURER_LAMBORGHINI = 'W';
    static final Map<Character, Market> MY2009_ALLOWED_MARKET_CHARS = Map.of(
            'E', Market.EUROPE,
            'U', Market.UNITED_STATES,
            'C', Market.CANADA,
            'A', Market.ARABIAN_COUNTRIES
    );
    static final Map<Character, EngineVariant> MY2009_ALLOWED_ENGINE_CHARS = Map.of(
            '6', EngineVariant.V12_62_580HP,
            '7', EngineVariant.V12_65_640HP,
            '8', EngineVariant.V12_65_640HP
    );
    static final Map<Character, TransmissionVariant> MY2009_ALLOWED_TRANSMISSION_CHARS = Map.of(
            'M', TransmissionVariant.MANUAL,
            'N', TransmissionVariant.MANUAL,
            'S', TransmissionVariant.EGEAR,
            'T', TransmissionVariant.EGEAR
    );
    static final Map<Character, BodyType> MY2009_ALLOWED_BODY_CHARS = Map.of(
            '1', BodyType.COUPE,
            '2', BodyType.ROADSTER,
            '3', BodyType.COUPE,
            '4', BodyType.ROADSTER,
            '7', BodyType.COUPE,
            '8', BodyType.COUPE,
            '9', BodyType.ROADSTER
    );

    //
    // Model year 2010
    //
    static final List<Character> MY2010_ALLOWED_DRIVETRAIN_CHARS = List.of('A', '1');
    static final Map<Character, TransmissionVariant> MY2010_ALLOWED_TRANSMISSION_CHARS = Map.of(
            'L', TransmissionVariant.MANUAL,
            'N', TransmissionVariant.MANUAL,
            'G', TransmissionVariant.EGEAR,
            'H', TransmissionVariant.EGEAR
    );
}