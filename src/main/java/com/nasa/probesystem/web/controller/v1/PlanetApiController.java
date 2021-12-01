package com.nasa.probesystem.web.controller.v1;

import com.nasa.probesystem.domain.model.dto.ProbeSystemRequest;
import com.nasa.probesystem.domain.model.dto.ProbeSystemResponse;
import com.nasa.probesystem.domain.service.DataAccessService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/navigation")
public class PlanetApiController implements PlanetApi {

  private final DataAccessService dataService;

  public PlanetApiController(DataAccessService dataService) {
    this.dataService = dataService;
  }

  @Override
  @PostMapping(value = "/planet", consumes = "application/json", produces = "application/json")
  public ResponseEntity<ProbeSystemResponse> createPlanet(@RequestBody ProbeSystemRequest request) {
    try {
      var created = dataService.createPlanet(request);
      log.info("Created planet {}", created);
      return new ResponseEntity<>(created, HttpStatus.CREATED);
    } catch (Exception ex) {
      log.info("Exception thrown when creating the planet with the request {}: {}", request, ex);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  @GetMapping(value = "/planet/{planetId}", produces = "application/json")
  public ResponseEntity<ProbeSystemResponse> getPlanet(@PathVariable int planetId) {
    try {
      var found = dataService.getPlanet(planetId);
      log.info("Planet found: {}", found);
      return new ResponseEntity<>(found, HttpStatus.FOUND);
    } catch (Exception ex) {
      log.info("Exception thrown when searching for the planet {}: {}", planetId, ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  @GetMapping(value = "/planet", produces = "application/json")
  public ResponseEntity<List<ProbeSystemResponse>> getAllPlanets() {
    try {
      var found = dataService.getAllPlanets();
      log.info("Planets found: {}", found);
      return new ResponseEntity<>(found, HttpStatus.FOUND);
    } catch (Exception ex) {
      log.info("Exception thrown when getting all planets: {}", ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  @PutMapping(value = "/planet/{planetId}", consumes = "application/json")
  public ResponseEntity<HttpStatus> updatePlanet(
      @PathVariable int planetId, @RequestBody ProbeSystemRequest request) {
    try {
      dataService.updatePlanet(planetId, request);
      log.info("Planet with id {} updated", planetId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception ex) {
      log.info("Exception thrown when updating planet with planetId{} {}", planetId, ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  @DeleteMapping(value = "/planet/{planetId}", produces = "application/json")
  public ResponseEntity<ProbeSystemResponse> deletePlanet(@PathVariable int planetId) {
    try {
      var found = dataService.deletePlanet(planetId);
      log.info("Deleted planet {} with id", found, planetId);
      return new ResponseEntity<>(found, HttpStatus.FOUND);
    } catch (Exception ex) {
      log.info("Exception thrown when deleting the planet {}: {}", planetId, ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
