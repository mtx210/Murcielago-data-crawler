package com.chadsoft.murci.integration.vinwiki;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VinWikiResponse {

    private List<Vehicle> vehicles;
}
