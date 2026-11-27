package com.sysprotect.receitasmedicas.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sysprotect.receitasmedicas.model.Receita;

public interface ReceitaRepository
        extends JpaRepository<Receita, UUID>,
                JpaSpecificationExecutor<Receita> {

    boolean existsByCodigoReceita(String codigoReceita);

    boolean existsByHashConteudo(String hashConteudo);

    Optional<Receita> findByCodigoReceita(String codigoReceita);
}