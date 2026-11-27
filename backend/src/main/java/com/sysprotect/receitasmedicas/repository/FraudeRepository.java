package com.sysprotect.receitasmedicas.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sysprotect.receitasmedicas.model.Fraude;

public interface FraudeRepository
        extends JpaRepository<Fraude, UUID>,
                JpaSpecificationExecutor<Fraude> {

    List<Fraude> findByReceitaId(UUID receitaId);

    boolean existsByReceitaIdAndResolvidaFalse(UUID receitaId);
}