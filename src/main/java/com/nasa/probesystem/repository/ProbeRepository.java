package com.nasa.probesystem.repository;

import com.nasa.probesystem.domain.model.Probe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProbeRepository extends JpaRepository<Probe, Integer> {}
