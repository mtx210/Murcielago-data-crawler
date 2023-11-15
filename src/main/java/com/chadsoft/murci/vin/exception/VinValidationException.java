package com.chadsoft.murci.vin.exception;

import lombok.Getter;

@Getter
public class VinValidationException extends Exception {

    private final String vin;
    private final String validationFailReason;

    public VinValidationException(String vin, String validationFailReason) {
        super();
        this.vin = vin;
        this.validationFailReason = validationFailReason;
    }
}