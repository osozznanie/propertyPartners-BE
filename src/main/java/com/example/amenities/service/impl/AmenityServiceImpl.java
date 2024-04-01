package com.example.amenities.service.impl;

import com.example.amenities.dto.AmenityDto;
import com.example.amenities.mapper.AmenityMapper;
import com.example.amenities.model.Amenity;
import com.example.amenities.repository.AmenityRepository;
import com.example.amenities.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmenityServiceImpl implements AmenityService {
    private final AmenityRepository amenityRepository;
    private final AmenityMapper amenityMapper;

    @Autowired
    public AmenityServiceImpl(AmenityRepository amenityRepository, AmenityMapper amenityMapper) {
        this.amenityRepository = amenityRepository;
        this.amenityMapper = amenityMapper;
    }

    @Override
    public List<AmenityDto> findAll() {
        return amenityRepository.findAll().stream().map(amenityMapper::toDTO).toList();
    }

    @Override
    public AmenityDto findById(String id) {
        return amenityRepository.findById(id).map(amenityMapper::toDTO).orElseThrow(
                () -> new IllegalArgumentException("Amenity not found with id: " + id)
        );
    }

    @Override
    public AmenityDto save(AmenityDto amenity) {
        Amenity entity = amenityMapper.toEntity(amenity);
        return amenityMapper.toDTO(amenityRepository.save(entity));
    }

    @Override
    public void deleteById(String id) {
        amenityRepository.deleteById(id);
    }
}
