package com.example.projectcosts_api.models;

import java.time.LocalDate;
import com.example.projectcosts_api.enums.Category;
import com.example.projectcosts_api.enums.converters.CategoryConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
  @Convert(converter = CategoryConverter.class)
  private Category category;
  private String description;
  private LocalDate startDate;
  private LocalDate endDate;

  /*public Project(Long id, String title, float budget, Category category, String description, LocalDate startDate,
      LocalDate endDate) {
    this.id = id;
    this.title = title;
    this.budget = budget;
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
    setCategory(category);
  }

  public Category getCategory(){
    if (this.category != null) {
      return Category.valueOf(this.category);
    }
    return null;
  }

  public void setCategory(Category category){
    if (category != null){
      this.category = category.getCode();
    }
  }*/

}
