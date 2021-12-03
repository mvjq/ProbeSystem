package com.nasa.probesystem.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.nasa.probesystem.domain.model.Direction;
import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import com.nasa.probesystem.repository.PlanetRepository;
import com.nasa.probesystem.repository.ProbeRepository;
import com.nasa.probesystem.util.TestUtils;
import javax.persistence.EntityNotFoundException;
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
  private ProbeRepository probeRepositoryMock = mock(ProbeRepository.class);
  private NavigationValidationService underTest =
      new NavigationValidationService(
          new DataAccessService(probeRepositoryMock, planetRepositoryMock));

  @Test
  void validPlanet_shouldReturnTrue() {
    Planet planet = TestUtils.givenGetValidPlanet("Mars", 5, 5, 1, planetRepositoryMock);
    assertTrue(underTest.validatePlanet(planet));
  }

  @Test
  void emptyPlanet_shouldThrowException() {
    Planet emptyPlanet = Planet.builder().build();
    assertThrows(EntityNotFoundException.class, () -> underTest.validatePlanet(emptyPlanet));
  }

  @Test
  void validProbe_shouldReturnTrue() {
    Planet planet = TestUtils.givenGetValidPlanet("Mars", 5, 5, 1, planetRepositoryMock);
    Probe probe = TestUtils.givenGetValidProbe("mars rover", 0, 0, Direction.N, planet);
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
    Planet planet = TestUtils.givenGetValidPlanet("Mars", 5, 5, 1, planetRepositoryMock);
    Probe probe = TestUtils.givenGetValidProbe("mars roover", 0, 0, Direction.N, planet);
    assertTrue(underTest.validateProbePositionInPlanet(probe, planet));
  }

  @Test
  void invalidProbePositionInPlanet_ProbeIsOutsideOfPlanetLimits_shouldReturnFalse() {
    Planet planet = TestUtils.givenGetValidPlanet("Mars", 5, 5, 1, planetRepositoryMock);
    Probe probe = TestUtils.givenGetValidProbe("mars roover", 10, 10, Direction.N, planet);
    assertFalse(underTest.validateProbePositionInPlanet(probe, planet));
  }

  @Test
  void invalidProbePositionInPlanet_CollisionWithProbes_shouldReturnFalse_1() {
    Planet planet = TestUtils.givenGetValidPlanet("Mars", 10, 10, 1, planetRepositoryMock);
    TestUtils.injectMultipleProbesInRepository(planet, planetRepositoryMock);
    Probe probe = TestUtils.givenGetValidProbe("mars roover #5", 0, 0, Direction.E, planet);
    assertFalse(underTest.validateProbePositionInPlanet(probe, planet));
  }

  @Test
  void invalidProbePositionInPlanet_CollisionWithProbes_shouldReturnFalse_2() {
    Planet planet = TestUtils.givenGetValidPlanet("Mars", 10, 10, 1, planetRepositoryMock);
    TestUtils.injectMultipleProbesInRepository(planet, planetRepositoryMock);
    Probe probe = TestUtils.givenGetValidProbe("mars roover #5", 1, 2, Direction.E, planet);
    assertFalse(underTest.validateProbePositionInPlanet(probe, planet));
  }

  @Test
  void invalidProbePositionInPlanet_CollisionWithProbes_shouldReturnFalse_3() {
    Planet planet = TestUtils.givenGetValidPlanet("Mars", 10, 10, 1, planetRepositoryMock);
    TestUtils.injectMultipleProbesInRepository(planet, planetRepositoryMock);
    Probe probe = TestUtils.givenGetValidProbe("mars roover #5", 3, 2, Direction.E, planet);
    assertFalse(underTest.validateProbePositionInPlanet(probe, planet));
  }
}
