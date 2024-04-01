package com.example.amenities.controller;

import com.example.amenities.dto.AmenityDto;
import com.example.amenities.service.AmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/amenities")
public class AmenityController {
    private final AmenityService amenityService;

    @PostMapping
    public AmenityDto createAmenity(AmenityDto amenityDto) {
        return amenityService.save(amenityDto);
    }

    @GetMapping
    public List<AmenityDto> getAllAmenities() {
        return amenityService.findAll();
    }

    @GetMapping("/{id}")
    public AmenityDto getAmenityById(@PathVariable String id) {
        return amenityService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAmenity(@PathVariable String id) {
        amenityService.deleteById(id);
    }

}
