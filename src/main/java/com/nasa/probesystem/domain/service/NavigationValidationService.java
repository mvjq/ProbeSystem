package com.nasa.probesystem.domain.service;

import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import com.nasa.probesystem.repository.PlanetRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NavigationValidationService implements NavigationValidation {

  private final PlanetRepository planetRepository;

  public NavigationValidationService(PlanetRepository planetRepository) {
    this.planetRepository = planetRepository;
  }

  @Override
  public Boolean validateProbe(Probe probe) {
    log.info("probe info: {}", probe);
    if (probe.getFaceDirection() == null || probe.getPlanet() == null) {
      log.info("Probe {} is not valid", probe);
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }

  @Override
  public Boolean validatePlanet(Planet planet) {

    var found = planetRepository.findById(planet.getId());
    if (found.isEmpty()
        || planet.getPlanetName() == null
        || planet.getMaxX() < 0
        || planet.getMaxY() < 0) {

      log.info("Planet {} is not valid", planet);
      return Boolean.FALSE;
    }
    log.info("Planet found in the database and its valid: {}");
    return Boolean.TRUE;
  }

  @Override
  public Boolean validateProbePositionInPlanet(Probe probe, Planet planet) {
    var validPlanet = planetRepository.findById(planet.getId()).orElse(null);
    if (validPlanet == null) return Boolean.FALSE;

    var listProbes = planetRepository.findAllProbesByplanetId(validPlanet.getId());
    if (!validateCollisionProbes(probe, listProbes) || !validateXandYOnPlanet(probe, planet)) {
      log.info("The probe {} cant move or land in the planet {}", probe, planet);
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }

  private Boolean validateXandYOnPlanet(Probe probe, Planet planet) {
    if (probe.getPositionX() > planet.getMaxX()
        || probe.getPositionY() > planet.getMaxY()
        || -probe.getPositionX() < -planet.getMaxX()
        || -probe.getPositionY() < -planet.getMaxY()) {
      log.info("The probe {} coordinates are not valid in the planet {}", probe, planet);
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }

  private Boolean validateCollisionProbes(Probe probe, List<Probe> listProbes) {
    for (Probe probeOnPlanet : listProbes) {
      if (!validateProbeCollision(probe, probeOnPlanet)) {
        log.info("The new probe {} entered a colision with probe {} probeOnPlanet");
        return Boolean.FALSE;
      }
    }
    return Boolean.TRUE;
  }

  private Boolean validateProbeCollision(Probe probe, Probe probeOnPlanet) {
    if (probe.getPositionX() == probeOnPlanet.getPositionX()
        || probe.getPositionY() == probeOnPlanet.getPositionY()) {
      log.info("A collision between probes happened");
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }
}
