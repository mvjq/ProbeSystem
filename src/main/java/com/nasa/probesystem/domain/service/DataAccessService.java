package com.nasa.probesystem.domain.service;

import com.nasa.probesystem.domain.model.Planet;
import com.nasa.probesystem.domain.model.Probe;
import com.nasa.probesystem.domain.model.dto.ProbeSystemRequest;
import com.nasa.probesystem.domain.model.dto.ProbeSystemResponse;
import com.nasa.probesystem.repository.PlanetRepository;
import com.nasa.probesystem.repository.ProbeRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataAccessService {
  private final ProbeRepository probeRepository;
  private final PlanetRepository planetRepository;

  public DataAccessService(ProbeRepository probeRepository, PlanetRepository planetRepository) {
    this.probeRepository = probeRepository;
    this.planetRepository = planetRepository;
  }

  @Transactional
  public ProbeSystemResponse savePlanet(ProbeSystemRequest request) throws Exception {
    if (request.getPlanet() == null) {
      throw new Exception("Request is not in the right format");
    }
    var saved = planetRepository.saveAndFlush(request.getPlanet());
    return ProbeSystemResponse.builder().planet(saved).build();
  }

  public void updatePlanet(Integer planetId, ProbeSystemRequest request) throws Exception {
    if (planetId == null || request.getPlanet() == null) {
      throw new Exception("Request is not in the right format");
    }

    planetRepository
        .findById(planetId)
        .ifPresentOrElse(
            planet -> planetRepository.saveAndFlush(request.getPlanet()),
            () -> {
              throw new EntityNotFoundException(String.valueOf(planetId));
            });
  }

  public ProbeSystemResponse getPlanet(int planetId) {
    return ProbeSystemResponse.builder()
        .planet(
            planetRepository
                .findById(planetId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(planetId))))
        .build();
  }

  public List<ProbeSystemResponse> getAllPlanets() {
    return planetRepository.findAll().stream()
        .map(planet -> ProbeSystemResponse.builder().planet(planet).build())
        .collect(Collectors.toList());
  }

  public ProbeSystemResponse deletePlanet(int planetId) {
    return planetRepository
        .findById(planetId)
        .map(
            planet -> {
              planetRepository.delete(planet);
              return ProbeSystemResponse.builder().planet(planet).build();
            })
        .orElseThrow(() -> new EntityNotFoundException("Planet not found"));
  }

  public ProbeSystemResponse getProbe(int probeId) {
    return ProbeSystemResponse.builder()
        .probe(
            probeRepository
                .findById(probeId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(probeId))))
        .build();
  }

  public List<ProbeSystemResponse> getAllProbes() {
    return probeRepository.findAll().stream()
        .map(probe -> ProbeSystemResponse.builder().probe(probe).build())
        .collect(Collectors.toList());
  }

  public ProbeSystemResponse deleteProbe(int probeId) {
    return probeRepository
        .findById(probeId)
        .map(
            probe -> {
              probeRepository.delete(probe);
              return ProbeSystemResponse.builder().probe(probe).build();
            })
        .orElseThrow(() -> new EntityNotFoundException("Probe not found"));
  }

  public List<Probe> getProbesLandedInAPlanet(int planetId) {
    return planetRepository.findAllProbesById(planetId);
  }
  
  public Probe saveProbe(Probe probe) {
    return probeRepository.saveAndFlush(probe);
  }
}
