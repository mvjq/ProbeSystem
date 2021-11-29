package com.nasa.probesystem.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Planet {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  int id;

  @NotBlank String planetName;
  @NotBlank int maxX;
  @NotBlank int maxY;

  public Planet() {}

  public Planet(String planetName, int maxX, int maxY) {
    this.maxX = maxX;
    this.maxY = maxY;
    this.planetName = planetName;
  }

  public void setMaxY(int maxY) {
    this.maxY = maxY;
  }

  public void setMaxX(int maxX) {
    this.maxX = maxX;
  }

  public int getMaxX() {
    return maxX;
  }

  public int getMaxY() {
    return maxY;
  }

  public String getPlanetName() {
    return planetName;
  }

  public Planet setPlanetName(String planetName) {
    this.planetName = planetName;
    return this;
  }

  @Override
  public String toString() {
    return "Planet {" + "maxX = " + maxX + ", maxY = " + maxY + '}';
  }
}
