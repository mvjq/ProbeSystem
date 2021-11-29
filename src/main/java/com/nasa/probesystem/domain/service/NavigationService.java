package com.nasa.probesystem.domain.service;

import com.nasa.probesystem.domain.model.Command;
import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NavigationService implements Navigation {
  private final NavigationValidationService validationService;

  public NavigationService(NavigationValidationService validationService) {
    this.validationService = validationService;
  }

  @Override
  public Planet navigate(Planet planet, Probe probe, List<Command> commands) {

    /*
    Vou receber um planeta válido e vou pousar esse probe nele
    esse pouso precisa ser validado,
    Depois vou receber uma lista de comandos que eu vou movimentar o probe na direção
    A cada movimento eu checo.
    */
    // TODO: melhorar isso e aplicar isso com operações de stream
    if (validationService.validatedInteractionProbePlanet(probe, planet)) {
      /*
      Ok, eu validei que a posição do probe está dentro do planeta
      validei também que eu naõ vou pousar em outro probe que tá no planeta (como eu vou buscar isso no banco?)
       */
      land(planet, probe);
      applyCommands(planet, probe, commands);
    }

    return new Planet("mars", 0, 1);
  }

  @Override
  public Planet land(Planet planet, Probe probe) {
    log.info("Landing the probe {} on planet {}", probe, planet);
    if (validationService.validatedInteractionProbePlanet(probe, planet)) {
      return planet;
    }
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
    return null;
  }

  @Override
  public Probe moveForward(Planet planet, Probe probe) {
    /*
    mover é um pouco mais dificil
    */
    log.info("Moving forwarding planet:{} probe:{}", planet, probe);

    switch (probe.getFaceDirection()) {
      case N:
      case W:
      case S:
      case E:
      default:
        log.info("Something went bad when moving the probe forward");
    }

    return null;
  }

  private Probe applyCommands(Planet planet, Probe probe, List<Command> commands) {
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

    // probe que agora tá em outro lugar
    return probe;
  }
}
