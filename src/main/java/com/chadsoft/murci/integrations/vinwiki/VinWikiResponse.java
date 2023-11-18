package com.chadsoft.murci.integrations.vinwiki;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
class VinWikiResponse {

    private List<Vehicle> vehicles;

    public VinWikiResponse() {
    }

    public VinWikiResponse(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
