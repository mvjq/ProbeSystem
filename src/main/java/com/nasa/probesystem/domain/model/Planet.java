package com.nasa.probesystem.domain.model;

public class Planet {
  private int maximumX;
  private int maximumY;

  public Planet(int maximumX, int maximumY) {
    this.maximumX = maximumX;
    this.maximumY = maximumY;
  }

  public void setMaximumY(int maximumY) {
    this.maximumY = maximumY;
  }

  public void setMaximumX(int maximumX) {
    this.maximumX = maximumX;
  }

  public int getMaximumX() {
    return maximumX;
  }

  public int getMaximumY() {
    return maximumY;
  }

  @Override
  public String toString() {
    return "Planet {" + "maximumX=" + maximumX + ", maximumY=" + maximumY + '}';
  }
}
