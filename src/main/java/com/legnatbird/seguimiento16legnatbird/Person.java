package com.legnatbird.seguimiento16legnatbird;

public class Person {
  public String id;
  public String name;
  public String lastName;
  public String gender;

  public String getId() {
    return id;
  }

  public Person(String id, String name, String lastName, String gender) {
    this.id = id;
    this.name = name;
    this.lastName = lastName;
    this.gender = gender;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }
}
