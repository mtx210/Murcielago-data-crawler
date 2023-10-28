package com.chadsoft.murci.vin;


import static com.chadsoft.murci.vin.VinConstants.OLD_VIN_PREFIX;

class VinUtils {

    private VinUtils() {}

    static String formatVinForValidation(String vin) {
        return vin.trim().toUpperCase();
    }

    static boolean isOldTypeVin(String vin) {
        return OLD_VIN_PREFIX.equals(vin.substring(0,3));
    }
}
