package com.nasa.probesystem.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.nasa.probesystem.domain.model.Command;
import com.nasa.probesystem.domain.model.Direction;
import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import com.nasa.probesystem.domain.model.dto.ProbeSystemRequest;
import com.nasa.probesystem.repository.PlanetRepository;
import com.nasa.probesystem.repository.ProbeRepository;
import com.nasa.probesystem.util.TestUtils;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;

class ProbeNavigationSystemTests {
  private final ProbeRepository probeRepositoryMock = mock(ProbeRepository.class);
  private final PlanetRepository planetRepositoryMock = mock(PlanetRepository.class);
  private final DataAccessService dataAccessService =
      new DataAccessService(probeRepositoryMock, planetRepositoryMock);
  private final NavigationValidationService navigationValidationMock =
      new NavigationValidationService(dataAccessService);
  private final NavigationService navigationServiceMock =
      new NavigationService(navigationValidationMock);
  private final ProbeNavigationSystem underTest =
      new ProbeNavigationSystem(navigationServiceMock, dataAccessService);

  @Test
  void createProbeNavigation_validPlanetValidProbe_shouldSucceed() throws Exception {
    Planet planet = TestUtils.givenGetValidPlanet("mars", 10, 10, 1, planetRepositoryMock);
    Probe probe = TestUtils.givenGetValidProbe("mars roove", 5, 5, Direction.N, planet);
    List<Command> listOfCommands = List.of(Command.L, Command.R, Command.M, Command.M);

      var landedResponse =
              underTest.createProbeNavigation(
                      ProbeSystemRequest.builder()
                              .planet(planet)
                              .probe(probe)
                              .commands(listOfCommands)
                              .build());
      System.out.println(landedResponse);
      assertEquals(landedResponse.getPlanet(), planet);
      assertEquals(landedResponse.getProbe(), probe);
  }

  @Test
  void createProbeNavigation_invalidPlanetValidProbe_shouldThrows() throws Exception {
    Planet planet = Planet.builder().build();
    Probe probe = TestUtils.givenGetValidProbe("mars roove", 5, 5, Direction.N, planet);
    List<Command> listOfCommands = List.of(Command.L, Command.R, Command.M, Command.M);

    assertThrows(
        EntityNotFoundException.class,
        () ->
            underTest.createProbeNavigation(
                ProbeSystemRequest.builder()
                    .planet(planet)
                    .probe(probe)
                    .commands(listOfCommands)
                    .build()));
  }

  @Test
  void createProbeNavigation_invalidPlanetInvalidProbe_shouldThrows() {
    Planet planet = Planet.builder().build();
    Probe probe = Probe.builder().build();
    List<Command> listOfCommands = List.of(Command.L, Command.R, Command.M, Command.M);

    assertThrows(
        Exception.class,
        () ->
            underTest.createProbeNavigation(
                ProbeSystemRequest.builder()
                    .planet(planet)
                    .probe(probe)
                    .commands(listOfCommands)
                    .build()));
  }

  @Test
  void createProbeNavigation_withCollision_shouldFail() throws Exception {
      Planet planet = TestUtils.givenGetValidPlanet("mars", 10, 10, 1, planetRepositoryMock);
      Probe probe = TestUtils.givenGetValidProbe("mars roove", 0, 0, Direction.N, planet);
      TestUtils.injectMultipleProbesInRepository(planet, planetRepositoryMock);
      List<Command> listOfCommands = List.of(Command.L, Command.R, Command.M, Command.M);

      assertThrows(
              Exception.class,
              () -> underTest.createProbeNavigation(
                      ProbeSystemRequest.builder()
                              .planet(planet)
                              .probe(probe)
                              .commands(listOfCommands)
                              .build()));
  }

  @Test
  void createProbeNavigation_moveOutOfBounds_shouldMoveToPlanetLimit() throws Exception {
      Planet planet = TestUtils.givenGetValidPlanet("mars", 4, 4, 1, planetRepositoryMock);
      Probe probe = TestUtils.givenGetValidProbe("mars roove", 0, 0, Direction.N, planet);
      List<Command> listOfCommands = List.of(Command.M, Command.M, Command.M, Command.M, Command.M, Command.M);


      var landedResponse =
              underTest.createProbeNavigation(
                      ProbeSystemRequest.builder()
                              .planet(planet)
                              .probe(probe)
                              .commands(listOfCommands)
                              .build());
      System.out.println(landedResponse);
      assertEquals(landedResponse.getPlanet(), planet);
      assertEquals(landedResponse.getProbe(), probe);
      assertEquals(landedResponse.getProbe().getPositionY(), landedResponse.getPlanet().getMaxY());
  }
}
