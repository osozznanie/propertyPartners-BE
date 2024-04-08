package com.example.projects.service;

import com.example.projects.dto.ProjectDto;
import com.example.projects.dto.ProjectFilter;

import java.util.List;

public interface ProjectService {
    List<ProjectDto> getAllProjects(ProjectFilter projectFilter);

    ProjectDto getProjectById(String id);

    ProjectDto createProject(ProjectDto projectDto, String amenitiesJson, String aboutJson, String floorPlan);

    ProjectDto updateProject(String id, ProjectDto projectDto);

    void deleteProject(String id);

    List<ProjectDto> getProjectsByFilter(String location, List<String> bedrooms, Double sizeFrom, Double sizeTo, Double priceFrom, Double priceTo, List<String> areas, List<String> completion);

    ProjectDto makeProjectTop(String id);

    ProjectDto makeProjectHidden(String id);
}
