package com.nasa.probesystem.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.nasa.probesystem.domain.model.Direction;
import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import com.nasa.probesystem.repository.PlanetRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NavigationValidationTests {

  private PlanetRepository planetRepositoryMock = mock(PlanetRepository.class);
  private NavigationValidationService underTest =
      new NavigationValidationService(planetRepositoryMock);

  @Test
  void validPlanet_shouldReturnTrue() {
    Planet planet = givenGetValidPlanet("Mars", 5, 5, 1);
    assertTrue(underTest.validatePlanet(planet));
  }

  @Test
  void emptyPlanet_shouldReturnFalse() {
    Planet emptyPlanet = Planet.builder().build();
    assertEquals(Boolean.FALSE, underTest.validatePlanet(emptyPlanet));
  }

  @Test
  void validProbe_shouldReturnTrue() {
    Planet planet = givenGetValidPlanet("Mars", 5, 5, 1);
    Probe probe = givenGetValidProbe("mars rover", 0, 0, Direction.N, planet);
    var flag = underTest.validateProbe(probe);
    assertTrue(flag);
  }

  @Test
  void emptyProbe_shouldReturnFalse() {
    Probe emptyProbe = Probe.builder().build();
    assertEquals(Boolean.FALSE, underTest.validateProbe(emptyProbe));
  }

  @Test
  void validProbePositionInPlanet_shouldReturnTrue() throws Exception {
    Planet planet = givenGetValidPlanet("Mars", 5, 5, 1);
    Probe probe = givenGetValidProbe("mars roover", 0, 0, Direction.N, planet);
    assertTrue(underTest.validateProbePositionInPlanet(probe, planet));
  }

  @Test
  void invalidProbePositionInPlanet_ProbeIsOutsideOfPlanetLimits_shouldReturnFalse() {
    Planet planet = givenGetValidPlanet("Mars", 5, 5, 1);
    Probe probe = givenGetValidProbe("mars roover", 10, 10, Direction.N, planet);
    assertFalse(underTest.validateProbePositionInPlanet(probe, planet));
  }

  @Test
  void invalidProbePositionInPlanet_CollisionWithProbes_shouldReturnFalse_1() {
    Planet planet = givenGetValidPlanet("Mars", 10, 10, 1);
    injectMultipleProbesInRepository(planet);
    Probe probe = givenGetValidProbe("mars roover #5", 0, 0, Direction.E, planet);
    assertFalse(underTest.validateProbePositionInPlanet(probe, planet));
  }

  @Test
  void invalidProbePositionInPlanet_CollisionWithProbes_shouldReturnFalse_2() {
    Planet planet = givenGetValidPlanet("Mars", 10, 10, 1);
    injectMultipleProbesInRepository(planet);
    Probe probe = givenGetValidProbe("mars roover #5", 1, 2, Direction.E, planet);
    assertFalse(underTest.validateProbePositionInPlanet(probe, planet));
  }

  @Test
  void invalidProbePositionInPlanet_CollisionWithProbes_shouldReturnFalse_3() {
    Planet planet = givenGetValidPlanet("Mars", 10, 10, 1);
    injectMultipleProbesInRepository(planet);
    Probe probe = givenGetValidProbe("mars roover #5", 3, 2, Direction.E, planet);
    assertFalse(underTest.validateProbePositionInPlanet(probe, planet));
  }

  private Planet givenGetValidPlanet(String name, int x, int y, int id) {
    var planet = Planet.builder().planetName(name).maxX(x).maxY(y).id(id).build();
    when(planetRepositoryMock.findById(planet.getId())).thenReturn(Optional.of(planet));
    return planet;
  }

  private Probe givenGetValidProbe(
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

  private void injectMultipleProbesInRepository(Planet planet) {
    var listProbes =
        List.of(
            givenGetValidProbe("mars roover #1", 0, 0, Direction.N, planet),
            givenGetValidProbe("mars roover #2", 1, 2, Direction.N, planet),
            givenGetValidProbe("mars roover #3", 2, 2, Direction.N, planet));
    when(planetRepositoryMock.findAllProbesByplanetId(planet.getId())).thenReturn((listProbes));
  }
}
