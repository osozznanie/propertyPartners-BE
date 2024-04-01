package com.example.projects.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanInfo {
    private String roomName;
    private String condition;
    private String type;
    private String name;
    private String woodwork;
    private Integer floor;
    private String heatingType;
    private String hotWater;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer toilets;
    private Boolean terrace;
    private String kitchen;
    private Double builderSurface;
    private Double usefulSurface;
    private Double terraceSurface;
}
