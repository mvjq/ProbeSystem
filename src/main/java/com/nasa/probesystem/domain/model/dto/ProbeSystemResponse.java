package com.nasa.probesystem.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProbeSystemResponse {
  private Planet planet;
  private Probe probe;
  private int planetId;
  private int probeId;
}
