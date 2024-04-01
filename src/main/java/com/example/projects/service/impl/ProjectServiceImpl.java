package com.example.projects.service.impl;

import com.example.projects.domain.FloorPlan;
import com.example.projects.domain.Plan;
import com.example.projects.domain.PlanInfo;
import com.example.projects.domain.Project;
import com.example.projects.dto.ProjectDto;
import com.example.projects.mapper.ProjectMapper;
import com.example.projects.repository.ProjectRepository;
import com.example.projects.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Override
    public ProjectDto getProjectById(String id) {
        return projectRepository.findById(id)
                .map(projectMapper::toDto)
                .orElseThrow(
                        () -> new IllegalArgumentException("No project is found by id = " + id)
                );
    }

    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        return projectMapper.toDto(projectRepository.save(projectMapper.toEntity(projectDto)));
    }

    @Override
    public ProjectDto updateProject(String id, ProjectDto projectDto) {
        Project projectForUpdate = projectRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("No project is found by id = " + id)
                );

        projectMapper.updateEntityFromDto(projectForUpdate, projectDto);

        return projectMapper.toDto(projectRepository.save(projectForUpdate));
    }

    @Override
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<ProjectDto> getProjectsByFilter(String location, List<String> bedrooms, Double sizeFrom, Double sizeTo, Double priceFrom, Double priceTo, List<String> areas, List<String> completion) {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .filter(project -> isProjectMatched(project, location, bedrooms, sizeFrom, sizeTo, priceFrom, priceTo, areas, completion))
                .map(projectMapper::toDto)
                .toList();
    }

    private boolean isProjectMatched(Project project, String location, List<String> bedrooms, Double sizeFrom, Double sizeTo, Double priceFrom, Double priceTo, List<String> areas, List<String> completion) {
        for (FloorPlan floorPlan : project.getFloorPlans().values()) {
            for (Plan plan : floorPlan.getPlans()) {
                PlanInfo planInfo = plan.getPlanInfo();
                if (planInfo != null) {
                    List<Integer> convertedBedrooms = bedrooms != null ? bedrooms.stream().map(Integer::parseInt).toList() : null;
                    if (convertedBedrooms != null && !convertedBedrooms.isEmpty() && !convertedBedrooms.contains(planInfo.getBedrooms())) {
                        return false;
                    }
                }
            }
        }

        return (location == null || project.getLocation().equals(location))
                && (sizeFrom == null || project.getSize() >= sizeFrom)
                && (sizeTo == null || project.getSize() <= sizeTo)
                && (priceFrom == null || project.getPrice() >= priceFrom)
                && (priceTo == null || project.getPrice() <= priceTo)
                && (areas == null || areas.contains(project.getArea()))
                && (completion == null || completion.contains(project.getCompletion()));
    }
}
