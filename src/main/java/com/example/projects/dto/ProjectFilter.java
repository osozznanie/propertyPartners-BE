package com.example.projects.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectFilter {
    private String name;
    private List<String> types;
    private List<Integer> rooms;
    private Double sizeFrom;
    private Double sizeTo;
    private Double priceFrom;
    private Double priceTo;
    private List<String> areas;
}
