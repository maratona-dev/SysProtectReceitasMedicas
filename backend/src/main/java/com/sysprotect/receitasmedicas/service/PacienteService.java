package com.sysprotect.receitasmedicas.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sysprotect.receitasmedicas.dto.request.PacienteRequestDto;
import com.sysprotect.receitasmedicas.dto.response.PacienteResponseDto;

public interface PacienteService {

    Page<PacienteResponseDto> findAll(Pageable pageable);

    Page<PacienteResponseDto> findAll(Pageable pageable, String nome, String cpf, Boolean ativo);

    PacienteResponseDto findById(UUID id);

    PacienteResponseDto create(PacienteRequestDto dto);

    PacienteResponseDto update(UUID id, PacienteRequestDto dto);

    void delete(UUID id);
}