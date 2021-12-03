package com.nasa.probesystem.domain.service;

import com.nasa.probesystem.domain.model.Command;
import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NavigationService implements Navigation {

  private final NavigationValidationService validationService;

  public NavigationService(final NavigationValidationService validationService) {
    this.validationService = validationService;
  }

  @Override
  public Probe navigate(Planet planet, Probe probe, final List<Command> commands) throws Exception {
    log.info("Probe {} Planet {} Commands {} ", probe, planet, commands);

    if (validationService.validateProbe(probe) && validationService.validatePlanet(planet)) {
      var landedPlanet = land(planet, probe);
      var landedProbe = applyCommands(planet, probe, commands);
      log.info(
          "Probe {} landed in the planet {} and after the commands {} its in the position x:{} y:{}",
          landedProbe,
          landedPlanet,
          commands,
          probe.getPositionX(),
          probe.getPositionY());

      return landedProbe;
    }
    throw new Exception("Invalid probe Navigation");
  }

  @Override
  public Planet land(Planet planet, Probe probe) throws Exception {
    if (validationService.validateProbePositionInPlanet(probe, planet)) {
      log.info("Landed probe {} on planet {}", probe, planet);
      probe.setPlanet(planet);
      return planet;
    }
    throw new Exception("Invalid landing of probe on planet {}");
  }

  private Probe applyCommands(Planet planet, Probe probe, final List<Command> commands)
      throws Exception {
    log.info("applying commands {} planet {} probe {}", commands, planet, probe);
    for (Command command : commands) {
      switch (command) {
        case M:
          moveForward(planet, probe);
          break;
        case L:
        case R:
          turn(probe, command);
          break;
        default:
          log.info("Command {} not found", command);
          throw new Exception("Command not found");
      }
    }
    return probe;
  }

  @Override
  public Probe turn(Probe probe, Command command) throws Exception {
    switch (command) {
      case L:
        probe.setFaceDirection(probe.getFaceDirection().getLeft());
        break;
      case R:
        probe.setFaceDirection(probe.getFaceDirection().getRight());
        break;
      default:
        log.info("Invalid probe turning direction command {}", command);
        throw new Exception("Invalid probe command");
    }
    log.info("Probe {} turned with command {}", probe, command);
    return probe;
  }

  @Override
  public Probe moveForward(Planet planet, Probe probe) throws Exception {
    log.info("Moving forwarding planet:{} probe:{}", planet, probe);
    switch (probe.getFaceDirection()) {
      case N:
        // (x = x, y + 1)
        return moveXandY(planet, probe, probe.getPositionX(), probe.getPositionY() + 1);
      case W:
        // (x - 1, y = y)
        return moveXandY(planet, probe, probe.getPositionX() - 1, probe.getPositionY());
      case S:
        // (x = x, y - 1)
        return moveXandY(planet, probe, probe.getPositionX(), probe.getPositionY() - 1);
      case E:
        // (x + 1, y = y)
        return moveXandY(planet, probe, probe.getPositionX() + 1, probe.getPositionY());
      default:
        log.info("Something went wrong with the probe {} moving forward", probe);
        throw new Exception("Invalid direction to move;");
    }
  }

  private Probe moveXandY(Planet planet, Probe probe, int newX, int newY) throws Exception {
    int oldProbeX = probe.getPositionX();
    int oldProbeY = probe.getPositionY();
    probe.setPositionX(newX);
    probe.setPositionY(newY);
    if (!validationService.validateProbePositionInPlanet(probe, planet)) {
      probe.setPositionX(oldProbeX);
      probe.setPositionY(oldProbeY);
      log.info(
          "This movement is invalid in the current planet {} old position x:{} y:{} new position x:{} y:{}",
          planet,
          oldProbeX,
          oldProbeY,
          newX,
          newY);
    }
    return probe;
  }
}
