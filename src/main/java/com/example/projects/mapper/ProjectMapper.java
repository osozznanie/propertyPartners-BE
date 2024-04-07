package com.example.projects.mapper;

import com.example.projects.domain.Project;
import com.example.projects.dto.ProjectDto;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ProjectMapper {

    public ProjectDto toDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setCompletion(project.getCompletion());
        projectDto.setSize(project.getSize());
        projectDto.setHidden(project.getHidden());
        projectDto.setPrice(project.getPrice());
        projectDto.setSizeM2(project.getSizeM2());
        projectDto.setLocation(project.getLocation());
        projectDto.setShortDescription(project.getShortDescription());
        projectDto.setPictures(project.getPictures());
        projectDto.setCoordinates(project.getCoordinates());
        projectDto.setArea(project.getArea());
        projectDto.setAbout(project.getAbout());
        projectDto.setAmenities(project.getAmenities());
        projectDto.setFloorPlans(project.getFloorPlans());
        projectDto.setTop(project.isTop());
        projectDto.setInformation(project.getInformation());

        return projectDto;
    }

    public Project toEntity(ProjectDto projectDto) {
        Project project = new Project();
        project.setId(projectDto.getId());
        project.setName(projectDto.getName());
        project.setCompletion(projectDto.getCompletion());
        project.setSize(projectDto.getSize());
        project.setHidden(projectDto.getHidden());
        project.setPrice(projectDto.getPrice());
        project.setSizeM2(projectDto.getSizeM2());
        project.setLocation(projectDto.getLocation());
        project.setShortDescription(projectDto.getShortDescription());
        project.setPictures(projectDto.getPictures());
        project.setCoordinates(projectDto.getCoordinates());
        project.setArea(projectDto.getArea());
        project.setAbout(projectDto.getAbout());
        project.setAmenities(projectDto.getAmenities());
        project.setFloorPlans(projectDto.getFloorPlans());
        project.setTop(projectDto.isTop());
        project.setInformation(projectDto.getInformation());

        return project;
    }

    public void updateEntityFromDto(Project project,ProjectDto projectDto){
        project.setId(projectDto.getId());
        project.setName(projectDto.getName());
        project.setCompletion(projectDto.getCompletion());
        project.setSize(projectDto.getSize());
        project.setHidden(projectDto.getHidden());
        project.setPrice(projectDto.getPrice());
        project.setSizeM2(projectDto.getSizeM2());
        project.setLocation(projectDto.getLocation());
        project.setShortDescription(projectDto.getShortDescription());
        project.setPictures(projectDto.getPictures());
        project.setCoordinates(projectDto.getCoordinates());
        project.setArea(projectDto.getArea());
        project.setAbout(projectDto.getAbout());
        project.setAmenities(projectDto.getAmenities());
        project.setFloorPlans(projectDto.getFloorPlans());
        project.setTop(projectDto.isTop());
        project.setInformation(projectDto.getInformation());
    }
}
