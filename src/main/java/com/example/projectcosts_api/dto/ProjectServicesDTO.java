package com.example.projectcosts_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectServicesDTO {
  private Long id;
  private String name;
  private float budget;
  private String description;

}
