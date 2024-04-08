package com.example.projects.service.impl;

import com.example.config.SpacesUploader;
import com.example.exception.EntityNotFoundException;
import com.example.projects.domain.FloorPlan;
import com.example.projects.domain.Plan;
import com.example.projects.domain.PlanInfo;
import com.example.projects.domain.Project;
import com.example.projects.dto.ProjectDto;
import com.example.projects.dto.ProjectFilter;
import com.example.projects.mapper.ProjectMapper;
import com.example.projects.repository.ProjectRepository;
import com.example.projects.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
        return getProjectsByFilter(
            projectDto.getLocation(),
            projectDto.getBedrooms(),
            projectDto.getSizeFrom(),
            projectDto.getSizeTo(),
            projectDto.getPriceFrom(),
            projectDto.getPriceTo(),
            projectDto.getAreas(),
            projectDto.getCompletion()
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
    public List<ProjectDto> getProjectsByFilter(String location, List<String> bedrooms, Double sizeFrom, Double sizeTo,
                                                Double priceFrom, Double priceTo, List<String> areas,
                                                List<String> completion) {
        List<Project> projects = projectRepository.findAll();

        return projects.stream()
            .filter(
                project -> isProjectMatched(project, location, bedrooms, sizeFrom, sizeTo, priceFrom, priceTo, areas,
                    completion))
            .map(projectMapper::toDto)
            .toList();
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

        return projectMapper.toDto(projectRepository.save(projectMapper.toEntity(projectDto)));
    }

    private boolean isProjectMatched(Project project, String location, List<String> bedrooms, Double sizeFrom,
                                     Double sizeTo, Double priceFrom, Double priceTo, List<String> areas,
                                     List<String> completion) {
        for (FloorPlan floorPlan : project.getFloorPlans().values()) {
            for (Plan plan : floorPlan.getPlans()) {
                PlanInfo planInfo = plan.getPlanInfo();
                if (planInfo != null) {
                    List<Integer> convertedBedrooms =
                        bedrooms != null ? bedrooms.stream().map(Integer::parseInt).toList() : null;
                    if (convertedBedrooms != null && !convertedBedrooms.isEmpty() && !convertedBedrooms.contains(
                        planInfo.getBedrooms())) {
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
