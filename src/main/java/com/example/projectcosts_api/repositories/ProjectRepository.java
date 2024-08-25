package com.example.projectcosts_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectcosts_api.models.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{
  List<Project> findByUserId(Long userId);
}
