package com.example.projects.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Description {
    private Integer totalFloors;
    private String type;
    private String floor;
    private Double area;
    private Integer rooms;
}
