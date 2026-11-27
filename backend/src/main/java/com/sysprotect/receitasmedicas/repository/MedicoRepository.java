package com.sysprotect.receitasmedicas.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sysprotect.receitasmedicas.model.Medicamento;

public interface MedicamentoRepository
        extends JpaRepository<Medicamento, UUID>,
                JpaSpecificationExecutor<Medicamento> {

    boolean existsByCodigoAnvisa(String codigoAnvisa);

    boolean existsByCodigoAnvisaAndIdNot(String codigoAnvisa, UUID id);

    Optional<Medicamento> findByCodigoAnvisa(String codigoAnvisa);
}