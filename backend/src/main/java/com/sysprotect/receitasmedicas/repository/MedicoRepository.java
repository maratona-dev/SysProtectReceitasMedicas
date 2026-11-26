package com.sysprotect.receitasmedicas.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sysprotect.receitasmedicas.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, UUID> {

    Optional<Medico> findByCrm(String crm);

    boolean existsByCrm(String crm);
}
