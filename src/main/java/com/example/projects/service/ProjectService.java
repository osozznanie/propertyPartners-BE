package com.example.projects.service;

import com.example.dto.ProjectDTO;

import java.util.List;


public interface ProjectService {
    ProjectDTO createProject(ProjectDTO projectDTO);
    ProjectDTO updateProject(ProjectDTO projectDTO);
    ProjectDTO getProjectById(String id);
    List<ProjectDTO> getAllProjects();
    void deleteProject(String id);
}
