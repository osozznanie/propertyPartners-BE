package com.example.projects.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    private Object image;
    private String name;
    private String location;
    private Description shortDescription;
    private Double price;
    private Double priceBym2;
    private PlanInfo planInfo;
}
