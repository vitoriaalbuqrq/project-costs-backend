package com.example.projectcosts_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projectcosts_api.models.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
