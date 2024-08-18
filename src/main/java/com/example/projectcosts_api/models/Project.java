package com.example.projectcosts_api.models;

import java.time.LocalDate;
import java.util.Set;

import com.example.projectcosts_api.enums.Category;
import com.example.projectcosts_api.enums.converters.CategoryConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private float budget;
  private float cost;
  @Convert(converter = CategoryConverter.class)
  private Category category;
  private String description;
  private LocalDate startDate;
  private LocalDate endDate;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
  private Set<ProjectServices> services;

  public boolean addService(ProjectServices service){
    if(this.cost + service.getBudget() > this.budget){
      return false;
    }
    service.setProject(this);
    this.services.add(service);
    this.cost += service.getBudget();
    return true;
  }

  public void removeService(ProjectServices service) {
    this.services.remove(service);
    this.cost -= service.getBudget();
  }
}
