package com.example.projects.service;

import com.example.projects.domain.FloorPlan;
import com.example.projects.domain.Plan;
import com.example.projects.domain.Project;
import com.example.projects.dto.ProjectDto;
import com.example.projects.dto.ProjectFilter;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    List<ProjectDto> getAllProjects(ProjectFilter projectFilter);

    ProjectDto getProjectById(String id);

    ProjectDto createProject(ProjectDto projectDto, String amenitiesJson, String aboutJson, String floorPlan);

    ProjectDto updateProject(String id, ProjectDto projectDto);

    void deleteProject(String id);

    List<Project> findByFilters(String name, List<String> types, List<Integer> rooms, Double sizeFrom, Double sizeTo,
                                Double priceFrom, Double priceTo, List<String> areas);

    ProjectDto makeProjectTop(String id);

    ProjectDto makeProjectHidden(String id);

    ProjectDto toggleHiddenInFloorPlanByProjectId(String id, String floorPlanId);

    ProjectDto addFloorPlanToProject(String projectId, Plan floorPlan, String numberOfFloorPlan);

    ProjectDto removePlanFromProject(String projectId, String planId);
}
