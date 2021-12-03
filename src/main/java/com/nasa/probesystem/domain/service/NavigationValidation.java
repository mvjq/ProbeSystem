package com.nasa.probesystem.domain.service;

import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;

public interface NavigationValidation {
  Boolean validateProbe(Probe probe);

  Boolean validatePlanet(Planet probe);

  Boolean validateProbePositionInPlanet(Probe probe, Planet planet);
}
