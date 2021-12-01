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
@RequestMapping("/api/v1/probeNavigation")
public class ProbeNavigationController implements ProbeNavigationApi {

  private final DataAccessService dataService;

  public ProbeNavigationController(DataAccessService dataService) {
    this.dataService = dataService;
  }

  @Override
  @PostMapping(
      value = "/probeNavigation",
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<ProbeSystemResponse> createProbeNavigation(
      @RequestBody ProbeSystemRequest request) {
    try {
      var created = dataService.createProbeNavigation(request);
      log.info("Created probe navigation {}", created);
      return new ResponseEntity<>(created, HttpStatus.CREATED);
    } catch (Exception ex) {
      log.info("Exception throw when creating a probe navigation request {}: {}", request, ex);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  @GetMapping(value = "/probeNavigation/{probeId}", produces = "application/json")
  public ResponseEntity<ProbeSystemResponse> getProbe(@PathVariable int probeId) {
    try {
      var found = dataService.getProbe(probeId);
      return new ResponseEntity<>(found, HttpStatus.FOUND);
    } catch (Exception ex) {
      log.info("Exception thrown when getting the probe: {}: {}", probeId, ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  @GetMapping(value = "/probeNavigation/", produces = "application/json")
  public ResponseEntity<List<ProbeSystemResponse>> getAllProbes() {
    try {
      var found = dataService.getAllProbes();
      log.info("Found probes: {}", found);
      return new ResponseEntity<>(found, HttpStatus.FOUND);
    } catch (Exception ex) {
      log.info("Exception thrown when getting all the probes: ", ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  @PutMapping(
      value = "/probeNavigation/{probeId}",
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<ProbeSystemResponse> updatedProbeNavigation(
      int probeId, ProbeSystemRequest request) {
    try {
      var updated = dataService.updatedProbeNavigation(probeId, request);
      log.info("ProbeNavigation updated {}", updated);
      return new ResponseEntity<>(updated, HttpStatus.OK);
    } catch (Exception ex) {
      log.info("Exception throw when updating the probe navigation with id {} {}", probeId, ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  @DeleteMapping(value = "/probeNavigation/{probeId}", produces = "application/json")
  public ResponseEntity<ProbeSystemResponse> deleteProbe(@PathVariable int probeId) {
    try {
      var deleted = dataService.deleteProbe(probeId);
      log.info("Deleted probe {}", deleted);
      return new ResponseEntity<>(deleted, HttpStatus.FOUND);
    } catch (Exception ex) {
      log.info("Exception thrown when deleting the probe: {}: ", probeId, ex);
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
