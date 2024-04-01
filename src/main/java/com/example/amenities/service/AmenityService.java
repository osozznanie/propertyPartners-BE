package com.example.amenities.service;

import com.example.amenities.dto.AmenityDto;

import java.util.List;

public interface AmenityService {
    List<AmenityDto> findAll();
    AmenityDto findById(String id);
    AmenityDto save(AmenityDto amenity);
    void deleteById(String id);
}
