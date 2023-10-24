package com.chadsoft.murci.tasks;

import lombok.Data;

import java.util.List;

@Data
public class VinValidationResults {

    private List<String> validVins;
    private List<String> invalidVins;
}
