package com.example.projects.service.impl;

import com.example.projects.dto.ProjectDto;
import com.example.projects.repository.ProjectRepository;
import com.example.projects.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public ProjectDto getProjectById(String id) {
        return null;
    }

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        return null;
    }

    @Override
    public ProjectDto updateProject(String id, ProjectDto projectDto) {
        return null;
    }

    @Override
    public void deleteProject(String id) {

    }

    @Override
    public List<ProjectDto> getProjectsByFilter(String location, List<String> types, List<String> bedrooms, Double sizeFrom, Double sizeTo, Double priceFrom, Double priceTo, List<String> areas, List<String> completion) {
        return
    }
}
