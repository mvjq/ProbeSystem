package com.nasa.probesystem.web.controller.v1;

import com.nasa.probesystem.domain.model.Planet;
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
public class NavigationApiController implements NavigationApi {

  @PostMapping(value = "/planet", consumes = "application/json", produces = "application/json")
  public ResponseEntity<Planet> createPlanet(@RequestBody Planet planet) {
    try {
      log.info("Created planet {}", planet);
      return new ResponseEntity<>(planet, HttpStatus.CREATED);
    } catch (Exception ex) {
      log.info("Exception thrown when creating the planet {}: {}", planet, ex);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping(value = "/planet/{planetName}", produces = "application/json")
  public ResponseEntity<Planet> getPlanet(@PathVariable String planetName) {
    try {
      log.info("Getting {}", planetName);
      Planet planet = new Planet(planetName, 1, 1);
      return new ResponseEntity<>(planet, HttpStatus.FOUND);
    } catch (Exception ex) {
      log.info("Exception thrown when searching for the planet {}: {}", planetName, ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(value = "/planet", produces = "application/json")
  public ResponseEntity<List<Planet>> getAllPlanets() {
    try {
      var planets = List.of(new Planet("mars", 1, 1));
      return new ResponseEntity<>(planets, HttpStatus.FOUND);
    } catch (Exception ex) {
      log.info("Exception thrown when getting all planets: {}", ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping(
      value = "/planet/{planetName}",
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<Planet> updatePlanet(
      @PathVariable String planetName, @RequestBody Planet planet) {
    try {
      log.info("updating planet {} {}", planetName, planet);
      return new ResponseEntity<>(planet, HttpStatus.FOUND);
    } catch (Exception ex) {
      log.info("Exception thrown when updating the plant {}: {}", planetName, ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping(value = "/planet/{planetName}", produces = "application/json")
  public ResponseEntity<Planet> deletePlanet(@PathVariable String planetName) {
    try {
      log.info("Deleting planet {}", planetName);
      Planet planet = new Planet(planetName, 1, 1);
      return new ResponseEntity<>(planet, HttpStatus.FOUND);
    } catch (Exception ex) {
      log.info("Exception thrown when deleting the planet {}: {}", planetName, ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
