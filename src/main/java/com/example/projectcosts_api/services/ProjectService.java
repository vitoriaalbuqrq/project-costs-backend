package com.example.projectcosts_api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.projectcosts_api.dto.ProjectDTO;
import com.example.projectcosts_api.dto.mapper.ProjectMapper;
import com.example.projectcosts_api.models.Project;
import com.example.projectcosts_api.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;

    @Transactional(readOnly = true)
    public List<ProjectDTO> findAll() {
        List<Project> result = projectRepository.findAll();
        return result.stream().map(projectMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectDTO findById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project não encontrado!"));
        return projectMapper.toDTO(project);
    }

    @Transactional
    public ProjectDTO create(ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        project = projectRepository.save(project);
        return projectMapper.toDTO(project);
    }

    @Transactional
    public ProjectDTO update(Long id, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));
        
        project.setTitle(projectDTO.getTitle());
        project.setDescription(projectDTO.getDescription());
        project.setBudget(projectDTO.getBudget());
        project.setCategory(projectMapper.convertCategoryValue(projectDTO.getCategory()));
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());

        project = projectRepository.save(project);
        return projectMapper.toDTO(project);
    }

    @Transactional
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }
}