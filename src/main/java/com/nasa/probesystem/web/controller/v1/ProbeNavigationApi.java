package com.nasa.probesystem.web.controller.v1;

import com.nasa.probesystem.domain.model.Probe;
import com.nasa.probesystem.domain.model.dto.ProbeSystemRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface ProbeNavigationApi {
  ResponseEntity<Probe> createProbeNavigation(ProbeSystemRequest request);

  ResponseEntity<Probe> updatedProbeNavigation(String probeId, String planetName, String commands);

  ResponseEntity<List<Probe>> getProbe(String probeId);

  ResponseEntity<Probe> deleteProbe(String probeId);
}
