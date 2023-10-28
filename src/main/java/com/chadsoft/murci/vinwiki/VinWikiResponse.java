package com.chadsoft.murci.vinwiki;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class VinWikiResponse {

    private List<Vehicle> vehicles;
}
