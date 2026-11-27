package com.sysprotect.receitasmedicas.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sysprotect.receitasmedicas.model.Medico;

public interface MedicoRepository
        extends JpaRepository<Medico, UUID>,
                JpaSpecificationExecutor<Medico> {

    boolean existsByCrm(String crm);

    boolean existsByCrmAndIdNot(String crm, UUID id);

    Optional<Medico> findByCrm(String crm);
}