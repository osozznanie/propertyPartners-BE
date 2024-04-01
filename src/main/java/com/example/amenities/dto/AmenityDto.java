package com.example.amenities.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AmenityDto {
    private String id;
    private String name;
    private String imageCode;
}
