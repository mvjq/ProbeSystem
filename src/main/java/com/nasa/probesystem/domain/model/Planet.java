package com.nasa.probesystem.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "planet")
public class Planet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "planetId")
  private int id;

  @Column(name = "planetName", length = 99, nullable = false, unique = true)
  private String planetName;

  @Column(name = "maxX", nullable = false)
  private int maxX;

  @Column(name = "maxY", nullable = false)
  private int maxY;

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

  public int getId() {
    return id;
  }

  public Planet setId(int id) {
    this.id = id;
    return this;
  }

  @Override
  public String toString() {
    return "Planet{"
        + "id="
        + id
        + ", planetName='"
        + planetName
        + '\''
        + ", maxX="
        + maxX
        + ", maxY="
        + maxY
        + '}';
  }
}
