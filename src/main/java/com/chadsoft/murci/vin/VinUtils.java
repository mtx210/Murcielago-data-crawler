package com.chadsoft.murci.vin;


import com.chadsoft.murci.vin.enums.VinType;

import static com.chadsoft.murci.vin.VinConstants.OLD_VIN_PREFIX;

class VinUtils {

    private VinUtils() {}

    static String formatVinForValidation(String vin) {
        return vin.trim().toUpperCase();
    }

    static VinType getVinType(String vin) {
        if (OLD_VIN_PREFIX.equals(vin.substring(0,3))) {
            return VinType.MY2003;
        }
        if ('A' == vin.charAt(9)) {
            return VinType.MY2010;
        }
        return VinType.MY2009;
    }
}
