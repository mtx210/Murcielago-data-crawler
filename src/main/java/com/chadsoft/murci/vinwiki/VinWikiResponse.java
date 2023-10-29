package com.chadsoft.murci.vinwiki;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;



@JsonIgnoreProperties(ignoreUnknown = true)
public class VinWikiResponse {

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
