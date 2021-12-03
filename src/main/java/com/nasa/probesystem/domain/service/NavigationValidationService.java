package com.nasa.probesystem.domain.service;

import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NavigationValidationService implements NavigationValidation {

  private final DataAccessService dataAccessService;

  public NavigationValidationService(DataAccessService dataAccessService) {
    this.dataAccessService = dataAccessService;
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

    var found = dataAccessService.getPlanet(planet.getId()).getPlanet();
    if (found == null
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
    var validPlanet = dataAccessService.getPlanet(planet.getId()).getPlanet();
    if (validPlanet == null) return Boolean.FALSE;

    if (!validateXandYOnPlanet(probe, planet)) {
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
}
