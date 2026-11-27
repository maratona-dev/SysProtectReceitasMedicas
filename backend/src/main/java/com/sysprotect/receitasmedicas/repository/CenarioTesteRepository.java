package com.sysprotect.receitasmedicas.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sysprotect.receitasmedicas.model.CenarioTeste;

public interface CenarioTesteRepository
        extends JpaRepository<CenarioTeste, UUID>,
                JpaSpecificationExecutor<CenarioTeste> {

    List<CenarioTeste> findByCondicaoTesteId(UUID condicaoTesteId);
}