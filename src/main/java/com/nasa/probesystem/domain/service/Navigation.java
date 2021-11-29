package com.nasa.probesystem.domain.service;

import com.nasa.probesystem.domain.model.Command;
import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import java.util.List;

public interface Navigation {
  Planet navigate(Planet planet, Probe probe, List<Command> commands);

  Planet land(Planet planet, Probe probe);

  Probe turn(Probe probe, Command command);

  Probe moveForward(Planet planet, Probe probe);
}
