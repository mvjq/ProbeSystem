package com.nasa.probesystem.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nasa.probesystem.domain.model.Command;
import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProbeSystemRequest {
  private Planet planet;
  private List<Probe> probes;
  private List<Command> commands;
}
