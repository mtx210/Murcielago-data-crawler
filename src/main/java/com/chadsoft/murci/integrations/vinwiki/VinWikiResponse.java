package com.chadsoft.murci.integrations.vinwiki;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;



@JsonIgnoreProperties(ignoreUnknown = true)
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
