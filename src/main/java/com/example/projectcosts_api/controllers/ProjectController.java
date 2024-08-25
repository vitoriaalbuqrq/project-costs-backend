package com.example.projectcosts_api.controllers;

import com.example.projectcosts_api.dto.ProjectDTO;
import com.example.projectcosts_api.models.user.User;
import com.example.projectcosts_api.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        ProjectDTO createdProject = projectService.createProject(authenticatedUser.getId(), projectDTO);
        return ResponseEntity.ok(createdProject);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        List<ProjectDTO> projectDTOs = projectService.getProjectsByUserId(authenticatedUser.getId());
        return ResponseEntity.ok(projectDTOs);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long projectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        ProjectDTO projectDTO = projectService.getProjectById(authenticatedUser.getId(), projectId);
        return ResponseEntity.ok(projectDTO);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long projectId, @RequestBody ProjectDTO projectDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        ProjectDTO updatedProject = projectService.updateProject(authenticatedUser.getId(), projectId, projectDTO);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        projectService.deleteProject(authenticatedUser.getId(), projectId);
        return ResponseEntity.noContent().build();
    }
}
