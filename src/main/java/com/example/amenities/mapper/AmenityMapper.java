package com.example.amenities.mapper;

import com.example.amenities.dto.AmenityDto;
import com.example.amenities.model.Amenity;
import org.springframework.stereotype.Component;

@Component
public class AmenityMapper {

    public AmenityDto toDTO(Amenity amenity) {
        return AmenityDto.builder()
                .id(amenity.getId())
                .name(amenity.getName())
                .imageCode(amenity.getImageCode())
                .build();
    }

    public Amenity toEntity(AmenityDto amenityDto) {
        return Amenity.builder()
                .id(amenityDto.getId())
                .name(amenityDto.getName())
                .imageCode(amenityDto.getImageCode())
                .build();
    }
}
