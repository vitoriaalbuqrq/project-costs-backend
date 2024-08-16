package com.example.projectcosts_api.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.projectcosts_api.dto.ProjectDTO;
import com.example.projectcosts_api.dto.ProjectServicesDTO;
import com.example.projectcosts_api.enums.Category;
import com.example.projectcosts_api.models.Project;
import com.example.projectcosts_api.models.ProjectServices;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProjectMapper {

    public ProjectDTO toDTO(Project project) {
        if (project == null) {
            return null;
        }
        Set<ProjectServicesDTO> serviceDTOs = project.getServices().stream()
                .map(this::toServiceDTO)
                .collect(Collectors.toSet());

        return new ProjectDTO(
                project.getId(),
                project.getTitle(),
                project.getBudget(),
                project.getCategory().getValue(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate(),
                serviceDTOs);
    }

    public Project toEntity(ProjectDTO projectDTO) {
        if (projectDTO == null) {
            return null;
        }
        System.out.println("Received ProjectDTO: " + projectDTO);

        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setTitle(projectDTO.getTitle());
        project.setBudget(projectDTO.getBudget());
        project.setCategory(convertCategoryValue(projectDTO.getCategory()));
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());

        System.out.println("ProjectDTO services: " + projectDTO.getServices());

        Set<ProjectServices> services = (projectDTO.getServices() != null)
                ? projectDTO.getServices().stream()
                        .map(this::toServiceEntity)
                        .collect(Collectors.toSet())
                : new HashSet<>();
        project.setServices(services);

        return project;
    }

    public ProjectServicesDTO toServiceDTO(ProjectServices service) {
        if (service == null) {
            return null;
        }
        return new ProjectServicesDTO(
                service.getId(),
                service.getName(),
                service.getBudget(),
                service.getDescription());
    }

    public ProjectServices toServiceEntity(ProjectServicesDTO serviceDTO) {
        if (serviceDTO == null) {
            return null;
        }
        ProjectServices service = new ProjectServices();
        service.setId(serviceDTO.getId());
        service.setName(serviceDTO.getName());
        service.setBudget(serviceDTO.getBudget());
        service.setDescription(serviceDTO.getDescription());
        return service;
    }

    public Category convertCategoryValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Design" -> Category.DESIGN;
            case "Development" -> Category.DEVELOPMENT;
            case "Marketing" -> Category.MARKETING;
            case "Research" -> Category.RESEARCH;
            case "Testing" -> Category.TESTING;
            case "Operations" -> Category.OPERATIONS;
            case "Sales" -> Category.SALES;
            case "Support" -> Category.SUPPORT;
            case "Finance" -> Category.FINANCE;
            case "Other" -> Category.OTHER;
            default -> throw new IllegalArgumentException("Categoria inválida: " + value);
        };
    }
}
