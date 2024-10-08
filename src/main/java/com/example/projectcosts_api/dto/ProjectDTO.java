package com.example.projectcosts_api.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
  private Long id;
  private String title;
  private float budget;
  private float cost;
  private String category;
  private String description;
  private LocalDate startDate;
  private LocalDate endDate;
  private Set<ProjectServicesDTO> services;
  private Long userId;
}
