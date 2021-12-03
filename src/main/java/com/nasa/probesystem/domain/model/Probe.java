package com.nasa.probesystem.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Probe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "probeId")
  private int id;

  @Column(name = "positionX")
  private int positionX;

  @Column(name = "positionY")
  private int positionY;

  @Column(name = "faceDirection")
  private Direction faceDirection;

  @Column(name = "probeName", nullable = false, unique = true)
  private String probeName;

  @ManyToOne
  @JoinColumn(name = "planetId")
  private Planet planet;

  @Override
  public String toString() {
    return "Probe{"
        + "id="
        + id
        + ", positionX="
        + positionX
        + ", positionY="
        + positionY
        + ", faceDirection="
        + faceDirection
        + ", probeName='"
        + probeName
        + '\''
        + ", planet="
        + planet
        + '}';
  }

  public int getId() {
    return id;
  }

  public Probe setId(int id) {
    this.id = id;
    return this;
  }

  public int getPositionX() {
    return positionX;
  }

  public Probe setPositionX(int positionX) {
    this.positionX = positionX;
    return this;
  }

  public int getPositionY() {
    return positionY;
  }

  public Probe setPositionY(int positionY) {
    this.positionY = positionY;
    return this;
  }

  public Direction getFaceDirection() {
    return faceDirection;
  }

  public Probe setFaceDirection(Direction faceDirection) {
    this.faceDirection = faceDirection;
    return this;
  }

  public String getProbeName() {
    return probeName;
  }

  public Probe setProbeName(String probeName) {
    this.probeName = probeName;
    return this;
  }

  public Planet getPlanet() {
    return planet;
  }

  public Probe setPlanet(Planet planet) {
    this.planet = planet;
    return this;
  }
}
