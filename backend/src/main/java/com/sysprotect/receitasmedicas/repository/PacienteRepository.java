package com.sysprotect.receitasmedicas.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sysprotect.receitasmedicas.model.CenarioTeste;

public interface PacienteRepository extends JpaRepository<CenarioTeste, UUID> {

}
