package com.chadsoft.murci.model.vin.exception;

public class VinValidationException extends Exception {

    private final String vin;
    private final String validationFailReason;

    public VinValidationException(String vin, String validationFailReason) {
        super();
        this.vin = vin;
        this.validationFailReason = validationFailReason;
    }

    public String getVin() {
        return vin;
    }

    public String getValidationFailReason() {
        return validationFailReason;
    }
}