package com.example.projects.controller;

import com.example.projects.dto.ProjectDto;
import com.example.projects.dto.ProjectFilter;
import com.example.projects.service.ProjectService;
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
    public ResponseEntity<ProjectDto> createProject(@ModelAttribute ProjectDto projectDto) {
        return ResponseEntity.ok(projectService.createProject(projectDto));
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
}
