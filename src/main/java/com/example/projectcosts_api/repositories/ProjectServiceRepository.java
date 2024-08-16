package com.example.projectcosts_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectcosts_api.models.ProjectServices;

public interface ProjectServiceRepository extends JpaRepository<ProjectServices, Long> {

  Optional<ProjectServices> findByIdAndProjectId(Long id, Long projectId);
}
