package com.example.projects.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(value = "projects")
public class Project {
    private String id;
    private String name;
    private String completion;
    private Double size;
    private Boolean hidden;
    private Double price;
    private Double sizeM2;
    private String location;

    @Field("short_description")
    private Description shortDescription;
    private List<Object> pictures;
    private Location coordinates;
    private String area;
    private Map<String, About> about;
    private List<Amenities> amenities;
    private Map<String, FloorPlan> floorPlans;
    private boolean isTop;

    private Information information;

    public void toggleTop() {
        this.isTop = !this.isTop;
    }

    public void toggleHidden() {
        this.hidden = !this.hidden;
    }
}
