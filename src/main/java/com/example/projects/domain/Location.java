package com.example.projects.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    private String address;
    private String city;
    private String state;
    private String zip;
    private GeoLocation geoLocation;
}
