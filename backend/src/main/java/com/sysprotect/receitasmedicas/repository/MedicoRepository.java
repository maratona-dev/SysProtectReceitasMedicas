package com.sysprotect.receitasmedicas.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sysprotect.receitasmedicas.model.Medico;

public interface MedicoRepository
        extends JpaRepository<Medico, UUID>,
                JpaSpecificationExecutor<Medico> {

}
