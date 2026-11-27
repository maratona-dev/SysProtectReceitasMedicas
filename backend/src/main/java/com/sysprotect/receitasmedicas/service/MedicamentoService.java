package com.sysprotect.receitasmedicas.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sysprotect.receitasmedicas.dto.request.MedicamentoRequestDto;
import com.sysprotect.receitasmedicas.dto.response.MedicamentoResponseDto;

public interface MedicamentoService {

    Page<MedicamentoResponseDto> findAll(Pageable pageable);

    Page<MedicamentoResponseDto> findAll(
            Pageable pageable, String nome, String principioAtivo, Boolean controlado, Boolean ativo);

    MedicamentoResponseDto findById(UUID id);

    MedicamentoResponseDto create(MedicamentoRequestDto dto);

    MedicamentoResponseDto update(UUID id, MedicamentoRequestDto dto);

    void delete(UUID id);
}