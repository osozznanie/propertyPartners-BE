package com.example.projects.controller;

import com.example.projects.domain.FloorPlan;
import com.example.projects.domain.Plan;
import com.example.projects.dto.ProjectDto;
import com.example.projects.dto.ProjectFilter;
import com.example.projects.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Projects", description = "API for managing projects")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "Create a new project")
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@ModelAttribute ProjectDto projectDto,
                                                    @RequestPart(value = "amenitiesInString") String amenitiesJson,
                                                    @RequestPart(value = "aboutInString") String aboutJson,
                                                    @RequestPart(value = "floorPlanInString") String floorPlanJson) {
        return ResponseEntity.ok(projectService.createProject(projectDto, amenitiesJson, aboutJson, floorPlanJson));
    }

    @Operation(summary = "Update an existing project")
    @PutMapping
    public ResponseEntity<ProjectDto> updateProject(@RequestBody ProjectDto projectDto) {
        return ResponseEntity.ok(projectService.updateProject(projectDto.getId(), projectDto));
    }

    @Operation(summary = "Get a project by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable String id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @Operation(summary = "Delete a project by its ID")
    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
    }

    @Operation(summary = "Get all projects that match the provided filter")
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects(@RequestBody ProjectFilter projectFilter) {
        return ResponseEntity.ok(projectService.getAllProjects(projectFilter));
    }

    @Operation(summary = "Make a project top by its ID")
    @GetMapping("/top/{id}")
    public ResponseEntity<ProjectDto> makeProjectTop(@PathVariable String id) {
        return ResponseEntity.ok(projectService.makeProjectTop(id));
    }

    @Operation(summary = "Make a project hidden by its ID")
    @GetMapping("/hidden/{id}")
    public ResponseEntity<ProjectDto> makeProjectHidden(@PathVariable String id) {
        return ResponseEntity.ok(projectService.makeProjectHidden(id));
    }

    @Operation(summary = "Toggle hidden in floor plan by project ID")
    @GetMapping("/toggleHiddenInFloorPlan/{id}")
    public ResponseEntity<ProjectDto> toggleHiddenInFloorPlanByProjectId(@PathVariable String id, @RequestParam String floorPlanId) {
        return ResponseEntity.ok(projectService.toggleHiddenInFloorPlanByProjectId(id, floorPlanId));
    }

    @Operation(summary = "Add floor plan to project")
    @PostMapping("/addFloorPlan/{projectId}")
    public ResponseEntity<ProjectDto> addFloorPlanToProject(@PathVariable String projectId,
                                                           @RequestPart(value = "floorPlan") String floorPlanJson,
                                                           @RequestPart(value = "numberOfFloorPlan") String numberOfFloorPlan) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Plan floorPlan = objectMapper.readValue(floorPlanJson, Plan.class);
            return ResponseEntity.ok(projectService.addFloorPlanToProject(projectId, floorPlan, numberOfFloorPlan));
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing floor plan", e);
        }
    }

    @Operation(summary = "Remove plan from project")
    @DeleteMapping("/removePlan/{projectId}/{planId}")
    public ResponseEntity<ProjectDto> removePlanFromProject(@PathVariable String projectId, @PathVariable String planId) {
        return ResponseEntity.ok(projectService.removePlanFromProject(projectId, planId));
    }

    @Operation(summary = "Get floor plan by floor number")
    @GetMapping("/floorPlan/{projectId}/{floorNumber}")
    public ResponseEntity<FloorPlan> getFloorPlanByFloorNumber(@PathVariable String projectId, @PathVariable String floorNumber) {
        return ResponseEntity.ok(projectService.getFloorPlanByFloorNumber(projectId, floorNumber));
    }
}
