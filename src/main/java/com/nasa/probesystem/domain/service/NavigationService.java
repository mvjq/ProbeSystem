package com.nasa.probesystem.domain.service;

import com.nasa.probesystem.domain.model.Command;
import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import com.nasa.probesystem.repository.PlanetRepository;
import com.nasa.probesystem.repository.ProbeRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NavigationService implements Navigation {
  private final NavigationValidationService validationService;
  private final PlanetRepository planetRepository;
  private final ProbeRepository probeRepository;

  public NavigationService(
      final NavigationValidationService validationService,
      final PlanetRepository planetRepository,
      final ProbeRepository probeRepository) {
    this.validationService = validationService;
    this.planetRepository = planetRepository;
    this.probeRepository = probeRepository;
  }

  @Override
  public Planet navigate(Planet planet, Probe probe, final List<Command> commands) {

    /*
    Vou receber um planeta válido e vou pousar esse probe nele
    esse pouso precisa ser validado,
    Depois vou receber uma lista de comandos que eu vou movimentar o probe na direção
    A cada movimento eu checo.
    */
    // TODO: melhorar isso e aplicar isso com operações de stream
    if (validationService.validatedProbePositionInPlanet(probe, planet)) {
      /*
      Ok, eu validei que a posição do probe está dentro do planeta
      validei também que eu naõ vou pousar em outro probe que tá no planeta (como eu vou buscar isso no banco?)
       */
      land(planet, probe);
      applyCommands(planet, probe, commands);
    }
    
    // TODO: implement error handling here

    return new Planet("mars", 0, 1);
  }

  @Override
  public Planet land(Planet planet, Probe probe) {
    log.info("Landing the probe {} on planet {}", probe, planet);
    if (validationService.validatedProbePositionInPlanet(probe, planet)) {
      return planet;
    }
    //TODO: implement error handling here
    return null;
  }

  @Override
  public Probe turn(Probe probe, Command command) {
    log.info("Command {} to move forward probe {}", probe, command);
    if (command.equals(Command.L)) {
      probe.setFaceDirection(probe.getFaceDirection().getLeft());
    } else if (command.equals(Command.R)) {
      probe.setFaceDirection(probe.getFaceDirection().getRight());
    }
    // implement erro handling here
    return null;
  }

  @Override
  public Probe moveForward(Planet planet, Probe probe) {

    log.info("Moving forwarding planet:{} probe:{}", planet, probe);
    switch (probe.getFaceDirection()) {
      case N:
        // (x = x, y + 1)
        moveXandY(probe, probe.getPositionX(), probe.getPositionY() + 1);
        break;
      case W:
        // (x - 1, y = y)
        moveXandY(probe, probe.getPositionX() - 1, probe.getPositionY());
        break;
      case S:
        // (x = x, y - 1)
        moveXandY(probe, probe.getPositionX(), probe.getPositionY() - 1);
        break;
      case E:
        // (x + 1, y = y)
        moveXandY(probe, probe.getPositionX() + 1, probe.getPositionY());
        break;
      default:
          // TODO: implemente erro handle here
        log.info("Something went bad when moving the probe forward");
    }

    // validate position (if its not correct, we thrown an error (InvalidCommandPosition or
    // something)
    // if is valid, we save the probe
    if (validationService.validatedProbePositionInPlanet(probe, planet)) {
      // save and return the probe
      log.info(
          "The probe {} move foward in the planet {} and stayed in a valid position",
          probe,
          planet);
      return null;
    }
    // TODO implement erro handling here
    log.info("Something went wrong with the probe {} moving forward", probe);
    return null;
  }

  private Probe applyCommands(Planet planet, Probe probe, final List<Command> commands) {
    log.info("applying commands {} planet {} probe {}", commands, planet, probe);
    commands.forEach(
        command -> {
          switch (command) {
            case M:
              moveForward(planet, probe);
              break;
            case L:
            case R:
              turn(probe, command);
              break;
            default:
              // TODO implement error handling here
              log.info("Command now found, throwing InvalidCommandException");
          }
        });
    return probe;
  }

  private void moveXandY(Probe probe, int x, int y) {
    probe.setPositionX(x);
    probe.setPositionY(y);
  }
}
