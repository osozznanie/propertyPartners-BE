package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {

    private String id;

    private String name;

    private Double price;

    private String about;

    private String smallDescription;

    private String property;

    private LocationDTO location;

    private List<String> amenities;

    private List<String> images;

}