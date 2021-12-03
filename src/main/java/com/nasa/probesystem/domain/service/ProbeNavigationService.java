package com.nasa.probesystem.domain.service;

import com.nasa.probesystem.domain.model.Probe;
import com.nasa.probesystem.domain.model.dto.ProbeSystemRequest;
import com.nasa.probesystem.domain.model.dto.ProbeSystemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProbeNavigationService {
  private final NavigationService navigationService;
  private final DataAccessService dataAccessService;

  public ProbeNavigationService(
      NavigationService navigationService, DataAccessService dataAccessService) {
    this.navigationService = navigationService;
    this.dataAccessService = dataAccessService;
  }

  public ProbeSystemResponse createProbeNavigation(ProbeSystemRequest request) throws Exception {
    if (request.getProbe() == null
        || request.getProbe().getPlanet() == null
        || request.getCommands() == null) {
      throw new Exception("Request is not in a valid format");
    }

    Probe probe = request.getProbe();
    navigationService.navigate(probe.getPlanet(), probe, request.getCommands());
    var response = ProbeSystemResponse.builder().planet(probe.getPlanet()).probe(probe).build();
    dataAccessService.saveProbe(probe);
    return response;
  }

  public ProbeSystemResponse updatedProbeNavigation(int probeId, ProbeSystemRequest request) {
    return null;
  }
}
