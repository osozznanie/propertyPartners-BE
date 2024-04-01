package com.example.projects.service;

import com.example.projects.dto.ProjectDto;

import java.util.List;

public interface ProjectService {
    List<ProjectDto> getAllProjects();

    ProjectDto getProjectById(String id);

    ProjectDto createProject(ProjectDto projectDto);

    ProjectDto updateProject(String id, ProjectDto projectDto);

    void deleteProject(String id);

    List<ProjectDto> getProjectsByFilter(String location, List<String> bedrooms, Double sizeFrom, Double sizeTo, Double priceFrom, Double priceTo, List<String> areas, List<String> completion);
}
