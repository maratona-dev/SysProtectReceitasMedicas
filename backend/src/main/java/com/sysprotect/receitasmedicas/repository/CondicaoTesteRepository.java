package com.sysprotect.receitasmedicas.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sysprotect.receitasmedicas.model.CondicaoTeste;

public interface CondicaoTesteRepository
        extends JpaRepository<CondicaoTeste, UUID>,
                JpaSpecificationExecutor<CondicaoTeste> {

    List<CondicaoTeste> findByAtivaTrue();

    List<CondicaoTeste> findByTipoFraudeId(UUID tipoFraudeId);
}