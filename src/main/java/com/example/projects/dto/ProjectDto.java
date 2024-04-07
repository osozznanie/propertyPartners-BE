package com.example.projects.dto;

import com.example.projects.domain.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ProjectDto {
    private String id;
    private String name;
    private String completion;
    private Double size;
    private Boolean hidden;
    private Double price;
    private Double sizeM2;
    private String location;

    private Description shortDescription;
    private List<Object> pictures;
    private Location coordinates;
    private String area;
    private Map<String,About> about;
    private List<Amenities> amenities;
    private Map<String,FloorPlan> floorPlans;
    private boolean isTop;

    private Information information;
}
