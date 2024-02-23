package com.example.projects.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {
    private String condition;

    private Integer rooms;
    private Integer bathrooms;
    private Integer toilets;
    private Integer bedrooms;

    private Boolean isTerrace;
    private Double terraceArea;

    private Integer kitchen;

    private String heatingType;

    private String externalWoodwork;
    private String hotWater;

    private Double usefulSurface;
    private Double constructedSurface;

    private String typeOfFloor;
}
