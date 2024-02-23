package com.example.projects.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(value = "projects")
public class Project {
    @Id
    private String id;
    private String name;
    private Double price;
    private About about;

    @Field("small_description")
    private Description smallDescription;
    private Property property;
    private Location location;
    private List<Amenities> amenities;
    private List<Object> images;
}
