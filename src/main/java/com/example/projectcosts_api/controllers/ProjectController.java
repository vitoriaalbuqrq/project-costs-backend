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

import com.example.projectcosts_api.dto.ProjectDTO;
import com.example.projectcosts_api.services.ProjectService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/projects")
public class ProjectController {
  @Autowired
  private ProjectService projectService;

  @GetMapping
  public List<ProjectDTO> findAll() {
    List<ProjectDTO> result = projectService.findAll();
    return result;
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProjectDTO> findById(@PathVariable Long id) {
    ProjectDTO projectDTO = projectService.findById(id);
    return ResponseEntity.ok(projectDTO);
  }

  @PostMapping
  public ResponseEntity<ProjectDTO> create(@RequestBody ProjectDTO projectDTO) {
    ProjectDTO saveProject = projectService.create(projectDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(saveProject);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProjectDTO> update(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
    ProjectDTO updatedPessoa = projectService.update(id, projectDTO);
    return ResponseEntity.ok(updatedPessoa);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    projectService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
