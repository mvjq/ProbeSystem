package com.nasa.probesystem.domain.model;

public class Probe {
  private int positionX;
  private int positionY;
  private Planet currentPlanet;
  private Direction faceDirection;

  public Probe(int positionX, int positionY, Planet currentPlanet, Direction faceDirection) {
    this.positionX = positionX;
    this.positionY = positionY;
    this.currentPlanet = currentPlanet;
    this.faceDirection = faceDirection;
  }

  public void setPositionX(int positionX) {
    this.positionX = positionX;
  }

  public void setPositionY(int positionY) {
    this.positionY = positionY;
  }

  public void setCurrentPlanet(Planet currentPlanet) {
    this.currentPlanet = currentPlanet;
  }

  public void setFaceDirection(Direction faceDirection) {
    this.faceDirection = faceDirection;
  }

  public int getPositionX() {
    return positionX;
  }

  public int getPositionY() {
    return positionY;
  }

  public Planet getCurrentPlanet() {
    return currentPlanet;
  }

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
        + ", currentPlanet="
        + currentPlanet
        + ", faceDirection="
        + faceDirection
        + '}';
  }
}
