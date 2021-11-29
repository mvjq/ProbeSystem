package com.nasa.probesystem.web.controller.v1;

import com.nasa.probesystem.domain.model.Direction;
import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import com.nasa.probesystem.domain.model.dto.ProbeSystemRequest;
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
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProbeNavigationController implements ProbeNavigationApi {

  @PostMapping(
      value = "/probeNavigation",
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<Probe> createProbeNavigation(@RequestBody ProbeSystemRequest request) {
    try {
      log.info("Creating probe navigation {}", request);
      Probe probe = new Probe(1, 1, new Planet("Name", 1, 1), Direction.E);
      return new ResponseEntity<>(probe, HttpStatus.CREATED);
    } catch (Exception ex) {
      log.info("Exception throw when creating a probe navigation request {}: {}", request, ex);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping(
      value = "/probeNavigation/{probeId}",
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<Probe> updatedProbeNavigation(
      @PathVariable String probeId, @RequestBody String planetName, @RequestBody String commands) {
    try {
      log.info(
          "Updating probeNavigation probename {}, planetName {} new commands {}",
          probeId,
          planetName,
          commands);
      Probe updatedProbe = new Probe(1, 1, new Planet("Name", 1, 1), Direction.E);
      return new ResponseEntity<>(updatedProbe, HttpStatus.FOUND);
    } catch (Exception ex) {
      log.info(
          "Exception throw when updating the probe navigation with new commands {} on planet {}: {}",
          commands,
          planetName,
          ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(value = "/probeNavigation/{probeId}", produces = "application/json")
  public ResponseEntity<List<Probe>> getProbe(@PathVariable String probeId) {
    try {
      List<Probe> probes = List.of(new Probe(1, 1, new Planet("Name", 1, 1), Direction.E));
      return new ResponseEntity<>(probes, HttpStatus.FOUND);
    } catch (Exception ex) {
      log.info("Exception thrown when getting the probe: {}: ", probeId, ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping(value = "/probeNavigation/{probeId}", produces = "application/json")
  public ResponseEntity<Probe> deleteProbe(@PathVariable String probeId) {
    try {
      log.info("Deleting probe {}", probeId);
      Probe removedProbe = new Probe(1, 1, new Planet("Name", 1, 1), Direction.E);
      return new ResponseEntity<>(removedProbe, HttpStatus.FOUND);
    } catch (Exception ex) {
      log.info("Exception thrown when deleting the probe: {}: ", probeId, ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
