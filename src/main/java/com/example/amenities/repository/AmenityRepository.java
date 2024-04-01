package com.example.amenities.repository;

import com.example.amenities.model.Amenity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AmenityRepository extends MongoRepository<Amenity, String> {
}
