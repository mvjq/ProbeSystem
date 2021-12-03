package com.nasa.probesystem.util;

import static org.mockito.Mockito.when;

import com.nasa.probesystem.domain.model.Direction;
import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import com.nasa.probesystem.repository.PlanetRepository;
import java.util.List;
import java.util.Optional;

public class TestUtils {

  public static Planet givenGetValidPlanet(
      String name, int x, int y, int id, PlanetRepository repository) {
    var planet = Planet.builder().planetName(name).maxX(x).maxY(y).id(id).build();
    when(repository.findById(planet.getId())).thenReturn(Optional.of(planet));
    return planet;
  }

  public static Probe givenGetValidProbe(
      String name, int x, int y, Direction faceDirection, Planet planet) {
    return Probe.builder()
        .probeName(name)
        .positionX(x)
        .positionY(y)
        .id(1)
        .planet(planet)
        .faceDirection(faceDirection)
        .build();
  }

  public static void injectMultipleProbesInRepository(Planet planet, PlanetRepository repository) {
    var listProbes =
        List.of(
            givenGetValidProbe("mars roover #1", 0, 0, Direction.N, planet),
            givenGetValidProbe("mars roover #2", 1, 2, Direction.N, planet),
            givenGetValidProbe("mars roover #3", 2, 2, Direction.N, planet));
    when(repository.findAllProbesById(planet.getId())).thenReturn((listProbes));
  }
}
