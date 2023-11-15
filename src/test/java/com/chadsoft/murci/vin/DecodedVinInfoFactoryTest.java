package com.chadsoft.murci.vin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecodedVinInfoFactoryTest {

    @Test
    void decodeFromVin() {
        DecodedVinInfo vin = DecodedVinInfoFactory.decodeFromVin("ZA9BC10E03LA12566").block();
        System.out.println(1);
    }
}