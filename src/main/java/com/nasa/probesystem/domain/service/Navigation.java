package com.nasa.probesystem.domain.service;

import com.nasa.probesystem.domain.model.Command;
import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import java.util.List;

public interface Navigation {
  Probe navigate(Planet planet, Probe probe, List<Command> commands) throws Exception;

  Planet land(Planet planet, Probe probe) throws Exception;

  Probe turn(Probe probe, Command command) throws Exception;

  Probe moveForward(Planet planet, Probe probe) throws Exception;
}
