package com.sysprotect.receitasmedicas.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sysprotect.receitasmedicas.model.Paciente;

public interface PacienteRepository
        extends JpaRepository<Paciente, UUID>,
                JpaSpecificationExecutor<Paciente> {

    boolean existsByCpf(String cpf);

    boolean existsByCpfAndIdNot(String cpf, UUID id);

    Optional<Paciente> findByCpf(String cpf);
}