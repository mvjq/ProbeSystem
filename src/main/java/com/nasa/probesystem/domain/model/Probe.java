package com.nasa.probesystem.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Probe {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  int id;

  @NotBlank int positionX;
  @NotBlank int positionY;
  // TODO: add join key here
  //  @NotBlank Planet currentPlanet;
  @NotBlank Direction faceDirection;

  public Probe(int positionX, int positionY, Planet currentPlanet, Direction faceDirection) {
    this.positionX = positionX;
    this.positionY = positionY;
    // this.currentPlanet = currentPlanet;
    this.faceDirection = faceDirection;
  }

  public Probe() {}

  public void setPositionX(int positionX) {
    this.positionX = positionX;
  }

  public void setPositionY(int positionY) {
    this.positionY = positionY;
  }

  //  public void setCurrentPlanet(Planet currentPlanet) {
  //    this.currentPlanet = currentPlanet;
  //  }

  public void setFaceDirection(Direction faceDirection) {
    this.faceDirection = faceDirection;
  }

  public int getPositionX() {
    return positionX;
  }

  public int getPositionY() {
    return positionY;
  }

  //  public Planet getCurrentPlanet() {
  //    return currentPlanet;
  //  }

  public Direction getFaceDirection() {
    return faceDirection;
  }

  @Override
  public String toString() {
    return "Probe {"
        + "positionX="
        + positionX
        + ", positionY="
        + positionY
        // + ", currentPlanet="
        // + currentPlanet
        + ", faceDirection="
        + faceDirection
        + '}';
  }
}
