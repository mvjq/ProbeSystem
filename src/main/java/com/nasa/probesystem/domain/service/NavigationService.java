package com.nasa.probesystem.domain.service;

import com.nasa.probesystem.domain.model.Command;
import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import java.util.List;

public class NavigationService implements Navigation {
  /*
  this module contains methods to handle logic related to navigation
  this is considered as a service because interactive with repository and others
  and the validation method
   */

  @Override
  public Planet navigate(Planet planet, Probe probe, List<Command> command) {
    return null;
  }

  @Override
  public Planet land(Planet planet, Probe probe) {
    return null;
  }

  @Override
  public Probe turn(Probe probe, Command command) {
    return null;
  }

  @Override
  public Probe moveForward(Planet planet, Probe probe) {
    return null;
  }
}
