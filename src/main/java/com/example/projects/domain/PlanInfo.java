package com.example.projects.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanInfo {
    private Double price;
    private Double size;
    private List<String> types;
    private String booking;
    private String construction;
    private String uponHandover;
    private String authorHandover;
}
