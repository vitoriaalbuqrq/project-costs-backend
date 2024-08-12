package com.example.projectcosts_api.dto.mapper;

import org.springframework.stereotype.Component;

import com.example.projectcosts_api.dto.ProjectDTO;
import com.example.projectcosts_api.enums.Category;
import com.example.projectcosts_api.models.Project;

@Component
public class ProjectMapper {

    public ProjectDTO toDTO(Project project) {
        if (project == null) {
            return null;
        }
        return new ProjectDTO(
            project.getId(),
            project.getTitle(),
            project.getBudget(),
            project.getCategory().getValue(),
            project.getDescription(),
            project.getStartDate(),
            project.getEndDate()
        );
    }

    public Project toEntity(ProjectDTO projectDTO) {
        if (projectDTO == null) {
            return null;
        }
        Project project = new Project();
        project.setId(projectDTO.getId());
        project.setTitle(projectDTO.getTitle());
        project.setBudget(projectDTO.getBudget());
        project.setCategory(convertCategoryValue(projectDTO.getCategory()));
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        return project;
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
