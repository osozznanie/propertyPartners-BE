package com.example.amenities.controller;

import com.example.amenities.dto.AmenityDto;
import com.example.amenities.service.AmenityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Amenities", description = "API for managing amenities")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/amenities")
public class AmenityController {
    private final AmenityService amenityService;

    @Operation(summary = "Create a new amenity")
    @PostMapping
    public AmenityDto createAmenity(AmenityDto amenityDto) {
        return amenityService.save(amenityDto);
    }

    @Operation(summary = "Get all amenities")
    @GetMapping
    public List<AmenityDto> getAllAmenities() {
        return amenityService.findAll();
    }

    @Operation(summary = "Get an amenity by its ID")
    @GetMapping("/{id}")
    public AmenityDto getAmenityById(@PathVariable String id) {
        return amenityService.findById(id);
    }

    @Operation(summary = "Delete an amenity by its ID")
    @DeleteMapping("/{id}")
    public void deleteAmenity(@PathVariable String id) {
        amenityService.deleteById(id);
    }
}