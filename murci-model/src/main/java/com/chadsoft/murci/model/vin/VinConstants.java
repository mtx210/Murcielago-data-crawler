package com.chadsoft.murci.model.vin;

import com.chadsoft.murci.model.vin.enums.BodyType;
import com.chadsoft.murci.model.vin.enums.EngineVariant;
import com.chadsoft.murci.model.vin.enums.Market;
import com.chadsoft.murci.model.vin.enums.TransmissionVariant;

import java.util.List;
import java.util.Map;

class VinConstants {

    private VinConstants() {}

    static final String OLD_VIN_PREFIX = "ZA9";
    static final int VIN_LENGTH = 17;
    static final Character SERIAL_NUMBER_PREFIX = 'A';
    static final Character REGION_EUROPE = 'Z';
    static final Character COUNTRY_ITALY_OLD = 'A';
    static final Character COUNTRY_ITALY_NEW = 'H';
    static final Character MODEL_MURCIELAGO = 'B';
    static final Character PLANT_BOLOGNA = 'L';
    static final Character MANUFACTURER_OLD = '9';
    static final String MANUFACTURER_OLD_SUFFIX = "A12";
    static final Character MANUFACTURER_NEW = 'W';
    static final Character BODY_OLD = 'C';
    static final Map<Character, BodyType> ALLOWED_BODY_NEW_CHARS = Map.of(
            '1', BodyType.COUPE,
            '2', BodyType.ROADSTER,
            '3', BodyType.COUPE,
            '4', BodyType.ROADSTER,
            '7', BodyType.COUPE,
            '8', BodyType.COUPE,
            '9', BodyType.ROADSTER
    );
    static final Map<Character, EngineVariant> ALLOWED_ENGINE_CHARS = Map.of(
            '6', EngineVariant.V12_62,
            '7', EngineVariant.V12_65
    );
    static final Map<Character, TransmissionVariant> ALLOWED_TRANSMISSION_CHARS = Map.of(
            'M', TransmissionVariant.MANUAL,
            'N', TransmissionVariant.MANUAL,
            'S', TransmissionVariant.EGEAR,
            'T', TransmissionVariant.EGEAR
    );
    static final String ENGINE_62 = "10";
    static final Map<Character, Market> ALLOWED_OLD_MARKET_CHARS = Map.of(
            'E', Market.EUROPE,
            'U', Market.UNITED_STATES,
            'C', Market.CANADA
    );
    static final Map<Character, Market> ALLOWED_NEW_MARKET_CHARS = Map.of(
            'E', Market.EUROPE,
            'U', Market.UNITED_STATES,
            'C', Market.CANADA,
            'A', Market.ASIA
    );
    static final List<Character> ALLOWED_CHECK_DIGITS_CHARS = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'X');
    static final List<Character> ALLOWED_MODEL_YEAR_DIGITS_CHARS = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A');
}