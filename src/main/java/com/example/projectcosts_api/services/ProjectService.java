package com.example.projectcosts_api.services;

import com.example.projectcosts_api.dto.ProjectDTO;
import com.example.projectcosts_api.dto.mapper.ProjectMapper;
import com.example.projectcosts_api.models.Project;
import com.example.projectcosts_api.models.ProjectServices;
import com.example.projectcosts_api.models.user.User;
import com.example.projectcosts_api.repositories.ProjectRepository;
import com.example.projectcosts_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectMapper projectMapper;

    public ProjectDTO createProject(Long userId, ProjectDTO projectDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Project project = projectMapper.toEntity(projectDTO);
        project.setUser(userOptional.get());
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDTO(savedProject);
    }

    public List<ProjectDTO> getProjectsByUserId(Long userId) {
        List<Project> projects = projectRepository.findByUserId(userId);
        return projects.stream().map(projectMapper::toDTO).toList();
    }

    public ProjectDTO getProjectById(Long userId, Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isEmpty()) {
            throw new RuntimeException("Projeto não encontrado");
        }

        Project project = projectOptional.get();
        if (!project.getUser().getId().equals(userId)) {
            throw new RuntimeException("Você não tem permissão para visualizar este projeto");
        }

        return projectMapper.toDTO(project);
    }

    public ProjectDTO updateProject(Long userId, Long projectId, ProjectDTO projectDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isEmpty()) {
            throw new RuntimeException("Projeto não encontrado");
        }

        Project project = projectOptional.get();
        if (!project.getUser().getId().equals(userId)) {
            throw new RuntimeException("Você não tem permissão para atualizar este projeto");
        }

        project.setTitle(projectDTO.getTitle());
        project.setBudget(projectDTO.getBudget());
        project.setCost(projectDTO.getCost());
        project.setCategory(projectMapper.convertCategoryValue(projectDTO.getCategory()));
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());

        Set<ProjectServices> services = (projectDTO.getServices() != null)
                ? projectDTO.getServices().stream()
                        .map(projectMapper::toServiceEntity)
                        .collect(Collectors.toSet())
                : new HashSet<>();
        project.setServices(services);

        Project updatedProject = projectRepository.save(project);
        return projectMapper.toDTO(updatedProject);
    }

    public void deleteProject(Long userId, Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isEmpty()) {
            throw new RuntimeException("Projeto não encontrado");
        }

        Project project = projectOptional.get();
        if (!project.getUser().getId().equals(userId)) {
            throw new RuntimeException("Você não tem permissão para excluir este projeto");
        }

        projectRepository.delete(project);
    }
}
