package com.chadsoft.murci.vinwiki;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class Vehicle {

    private String make;
    private String model;
    private String year;
    private String trim;
    @JsonProperty("long_name")
    private String longName;
    @JsonProperty("poster_photo")
    private String posterPhoto;
    @JsonProperty("icon_photo")
    private String iconPhoto;
    private Integer id;
    private String vin;
    @JsonProperty("follower_count")
    private Integer followerCount;
    @JsonProperty("post_count")
    private Integer postCount;
}