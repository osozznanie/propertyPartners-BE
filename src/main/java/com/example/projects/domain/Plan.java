package com.example.projects.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    private String id;
    private Object image;
    private String name;
    private Double price;
    private Double priceBym2;
    private PlanInfo planInfo;
    private boolean isHidden;

    public void toggleHidden() {
        this.isHidden = !this.isHidden;
    }
}
