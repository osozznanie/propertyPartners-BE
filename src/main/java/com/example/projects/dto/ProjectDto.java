package com.example.projects.dto;

import com.example.projects.domain.About;
import com.example.projects.domain.Amenities;
import com.example.projects.domain.Description;
import com.example.projects.domain.FloorPlan;
import com.example.projects.domain.Information;
import com.example.projects.domain.Location;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
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
    private Map<String, About> about;
    private List<Amenities> amenities;
    private Map<String, FloorPlan> floorPlans;
    private boolean isTop;

    private Information information;

    public void setAmenitiesFromStrings(String amenitiesJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.amenities = objectMapper.readValue(amenitiesJson, new TypeReference<List<Amenities>>(){});
        } catch (IOException e) {
            log.error("Error parsing amenities", e);
        }
    }

    public void setFloorPlansFromStrings(String floorPlansJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.floorPlans = objectMapper.readValue(floorPlansJson, new TypeReference<Map<String, FloorPlan>>(){});
        } catch (IOException e) {
            log.error("Error parsing floor plans", e);
        }
    }

    public void setAboutFromStrings(String aboutJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.about = objectMapper.readValue(aboutJson, new TypeReference<Map<String, About>>(){});
        } catch (IOException e) {
            log.error("Error parsing about", e);
        }
    }
}
