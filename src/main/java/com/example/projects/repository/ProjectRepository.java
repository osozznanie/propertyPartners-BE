package com.example.projects.repository;

import com.example.projects.domain.Project;
import java.util.List;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {

    @Aggregation(pipeline = {
        "{ $match: { 'name': ?0 } }",
        "{ $match: { 'types': { $in: ?1 } } }",
        "{ $match: { 'short_description.rooms': { $in: ?2 } } }",
        "{ $match: { 'size': { $gte: ?3, $lte: ?4 } } }",
        "{ $match: { 'price': { $gte: ?5, $lte: ?6 } } }",
        "{ $match: { 'area': { $in: ?7 } } }"
    })
    List<Project> findByFilters(String name, List<String> types, List<Integer> rooms, Double sizeFrom, Double sizeTo,
                                Double priceFrom, Double priceTo, List<String> areas);


}
