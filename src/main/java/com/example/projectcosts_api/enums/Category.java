package com.example.projectcosts_api.enums;

public enum Category {
  DESIGN("Design"),
  DEVELOPMENT("Development"),
  MARKETING("Marketing"),
  RESEARCH("Research"),
  TESTING("Testing"),
  OPERATIONS("Operations"),
  SALES("Sales"),
  SUPPORT("Support"),
  FINANCE("Finance"),
  OTHER("Other");

  private String value;

  private Category(String value){
    this.value = value;
  }

  public String getValue(){
    return value;
  }

  @Override
  public String toString(){
    return value;
  }
}
