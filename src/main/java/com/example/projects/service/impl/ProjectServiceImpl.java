package com.example.projects.service.impl;

import com.example.config.SpacesUploader;
import com.example.exception.EntityNotFoundException;
import com.example.projects.domain.FloorPlan;
import com.example.projects.domain.Plan;
import com.example.projects.domain.Project;
import com.example.projects.dto.ProjectDto;
import com.example.projects.dto.ProjectFilter;
import com.example.projects.mapper.ProjectMapper;
import com.example.projects.repository.ProjectRepository;
import com.example.projects.service.ProjectService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final SpacesUploader spacesUploader;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper,
                              SpacesUploader spacesUploader) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.spacesUploader = spacesUploader;
    }

    @Override
    public List<ProjectDto> getAllProjects(ProjectFilter projectDto) {
        return findByFilters(
            projectDto.getName(),
            projectDto.getTypes(),
            projectDto.getRooms(),
            projectDto.getSizeFrom(),
            projectDto.getSizeTo(),
            projectDto.getPriceFrom(),
            projectDto.getPriceTo(),
            projectDto.getAreas()
        ).stream()
            .map(projectMapper::toDto)
            .toList(
            );
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
    public ProjectDto createProject(ProjectDto projectDto, String amenitiesJson, String aboutJson, String floorPlan) {
        if (projectDto.getPictures() != null && projectDto.getFloorPlans() != null) {
            for (int i = 0; i < projectDto.getPictures().size(); i++) {
                if (projectDto.getPictures().get(i) instanceof MultipartFile) {
                    String url = spacesUploader.uploadAndReturnUrl((MultipartFile) projectDto.getPictures().get(i));
                    projectDto.getPictures().remove(i);
                    projectDto.getPictures().add(i, url);
                }
            }

            for (Map.Entry<String, FloorPlan> entry : projectDto.getFloorPlans().entrySet()) {

                for (Plan plan : entry.getValue().getPlans()) {
                    if (plan.getImage() instanceof MultipartFile) {
                        String url = spacesUploader.uploadAndReturnUrl((MultipartFile) plan.getImage());
                        plan.setImage(url);
                    }
                }
            }
        }

        if (projectDto.getInformation() != null) {
            String urlQrCode = spacesUploader.uploadAndReturnUrl(
                (MultipartFile) projectDto.getInformation().getQrCode());
            projectDto.getInformation().setQrCode(urlQrCode);
            projectDto.getInformation().setDateOfExpiration(new Date());

            String fileUrl = spacesUploader.uploadAndReturnUrl(
                (MultipartFile) projectDto.getInformation().getInsertFileHere());
            projectDto.getInformation().setInsertFileHere(fileUrl);
        }

        projectDto.setAmenitiesFromStrings(amenitiesJson);
        projectDto.setFloorPlansFromStrings(floorPlan);
        projectDto.setAboutFromStrings(aboutJson);

        if (projectDto.getFloorPlans() != null) {
            projectDto.getFloorPlans().values().forEach(plan -> {
                plan.getPlans().forEach(this::setRandomIfForFloorPlan);
            });
        }

        return projectMapper.toDto(projectRepository.save(projectMapper.toEntity(projectDto)));
    }

    @Override
    public ProjectDto updateProject(String id, ProjectDto projectDto) {
        Project projectForUpdate = projectRepository.findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("No project is found by id = " + id)
            );

        projectMapper.updateEntityFromDto(projectForUpdate, projectDto);

        return projectMapper.toDto(projectRepository.save(projectForUpdate));
    }

    @Override
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<Project> findByFilters(String name, List<String> types, List<Integer> rooms, Double sizeFrom,
                                       Double sizeTo, Double priceFrom, Double priceTo, List<String> areas) {
        return projectRepository.findByFilters(
            name, types, rooms, sizeFrom, sizeTo, priceFrom, priceTo, areas);
    }

    @Override
    public ProjectDto makeProjectTop(String id) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Project with id " + id + " not found"));
        project.toggleTop();
        projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Override
    public ProjectDto makeProjectHidden(String id) {
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Project with id " + id + " not found"));
        project.toggleHidden();
        projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Override
    public ProjectDto toggleHiddenInFloorPlanByProjectId(String id, String floorPlanId) {
        ProjectDto projectDtoById = getProjectById(id);

        Plan plan = projectDtoById.getFloorPlans().values().stream()
            .flatMap(floorPlan -> floorPlan.getPlans().stream())
            .filter(p -> p.getId().equals(floorPlanId))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("Plan with id " + floorPlanId + " not found"));

        plan.toggleHidden();

        return updateProject(id, projectDtoById);
    }

    @Override
    public ProjectDto addFloorPlanToProject(String projectId, Plan floorPlan, String numberOfFloorPlan) {
        ProjectDto projectDtoById = getProjectById(projectId);

        projectDtoById.getFloorPlans().get(numberOfFloorPlan).getPlans().add(floorPlan);

        return updateProject(projectId, projectDtoById);
    }

    @Override
    public ProjectDto removePlanFromProject(String projectId, String planId) {
        ProjectDto projectDtoById = getProjectById(projectId);

        projectDtoById.getFloorPlans().values().forEach(floorPlan -> {
            floorPlan.getPlans().removeIf(plan -> plan.getId().equals(planId));
        });

        return updateProject(projectId, projectDtoById);
    }

    @Override
    public FloorPlan getFloorPlanByFloorNumber(String projectId, String floorNumber) {
        ProjectDto projectDtoById = getProjectById(projectId);

        return projectDtoById.getFloorPlans().get(floorNumber);
    }

    private void setRandomIfForFloorPlan(Plan floorPlan) {
        if (floorPlan.getId() == null) {
            floorPlan.setId(UUID.randomUUID().toString());
        }
    }

}
