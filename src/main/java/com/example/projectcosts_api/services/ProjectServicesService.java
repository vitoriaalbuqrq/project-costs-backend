package com.example.projectcosts_api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.projectcosts_api.dto.ProjectServicesDTO;
import com.example.projectcosts_api.dto.mapper.ProjectMapper;
import com.example.projectcosts_api.models.Project;
import com.example.projectcosts_api.models.ProjectServices;
import com.example.projectcosts_api.repositories.ProjectRepository;
import com.example.projectcosts_api.repositories.ProjectServiceRepository;

@Service
public class ProjectServicesService {

    @Autowired
    private ProjectServiceRepository serviceRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectMapper projectMapper;

    @Transactional(readOnly = true)
    public List<ProjectServicesDTO> findAllByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));
        return project.getServices().stream()
                .map(projectMapper::toServiceDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectServicesDTO findById(Long projectId, Long serviceId) {
        ProjectServices projectService = serviceRepository.findByIdAndProjectId(serviceId, projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado para o projeto"));

        return projectMapper.toServiceDTO(projectService);
    }

    @Transactional
    public ProjectServicesDTO create(Long projectId, ProjectServicesDTO projectServicesDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));

        ProjectServices projectService = projectMapper.toServiceEntity(projectServicesDTO);

        boolean added = project.addService(projectService);
        if (!added) {
            throw new IllegalArgumentException("O custo total dos serviços excede o orçamento do projeto.");
        }

        projectService = serviceRepository.save(projectService);
        projectRepository.save(project); 

        return projectMapper.toServiceDTO(projectService);
    }

    @Transactional
    public ProjectServicesDTO update(Long projectId, Long serviceId, ProjectServicesDTO projectServicesDTO) {
        ProjectServices projectService = serviceRepository.findByIdAndProjectId(serviceId, projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado para o projeto"));

        projectService.setName(projectServicesDTO.getName());
        projectService.setBudget(projectServicesDTO.getBudget());
        projectService.setDescription(projectServicesDTO.getDescription());

        projectService = serviceRepository.save(projectService);
        return projectMapper.toServiceDTO(projectService);
    }

    @Transactional
    public void delete(Long projectId, Long serviceId) {
        ProjectServices projectService = serviceRepository.findByIdAndProjectId(serviceId, projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço não encontrado para o projeto"));

        Project project = projectService.getProject();
        project.removeService(projectService);
    
        serviceRepository.delete(projectService);
        projectRepository.save(project);
    }
}
