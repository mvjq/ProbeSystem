package com.nasa.probesystem.domain.model;

public enum Direction {
  N,
  W,
  E,
  S;

  private Direction left;
  private Direction right;

  static {
    N.left = W;
    N.right = E;

    W.left = S;
    W.right = N;

    S.left = W;
    S.right = E;

    E.left = N;
    E.right = S;
  }

  public Direction getLeft() {
    return left;
  }

  public Direction getRight() {
    return right;
  }
}
