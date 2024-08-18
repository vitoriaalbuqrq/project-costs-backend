package com.example.projectcosts_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projectcosts_api.dto.ProjectServicesDTO;
import com.example.projectcosts_api.services.ProjectServicesService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/projects/{projectId}/services")
public class ServicesController {
    
    @Autowired
    private ProjectServicesService projectServicesService;

    @GetMapping
    public ResponseEntity<List<ProjectServicesDTO>> findAll(@PathVariable Long projectId) {
        List<ProjectServicesDTO> result = projectServicesService.findAllByProjectId(projectId);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ProjectServicesDTO> addService(@PathVariable Long projectId, @RequestBody ProjectServicesDTO projectServicesDTO) {
        ProjectServicesDTO createdService = projectServicesService.create(projectId, projectServicesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdService);
    }

    @GetMapping("/{serviceId}")
    public ResponseEntity<ProjectServicesDTO> findById(@PathVariable Long projectId, @PathVariable Long serviceId) {
        ProjectServicesDTO serviceDTO = projectServicesService.findById(projectId, serviceId);
        return ResponseEntity.ok(serviceDTO);
    }

    @PutMapping("/{serviceId}")
    public ResponseEntity<ProjectServicesDTO> update(@PathVariable Long projectId, @PathVariable Long serviceId, @RequestBody ProjectServicesDTO projectServicesDTO) {
        ProjectServicesDTO updatedService = projectServicesService.update(projectId, serviceId, projectServicesDTO);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<Void> delete(@PathVariable Long projectId, @PathVariable Long serviceId) {
        projectServicesService.delete(projectId, serviceId);
        return ResponseEntity.noContent().build();
    }
}