package com.chadsoft.murci.vin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecodedVinInfoFactoryTest {

    @Test
    void decodeFromVin() {
        DecodedVinInfo vin = DecodedVinInfoFactory.decodeFromVin("ZHWBE37S07LA02519").block();
        System.out.println(1);
    }
}