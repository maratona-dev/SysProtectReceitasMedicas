package com.sysprotect.receitasmedicas.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sysprotect.receitasmedicas.dto.request.TipoFraudeRequestDto;
import com.sysprotect.receitasmedicas.dto.response.TipoFraudeResponseDto;
import com.sysprotect.receitasmedicas.enums.CategoriaFraude;

public interface TipoFraudeService {

    Page<TipoFraudeResponseDto> findAll(Pageable pageable);

    Page<TipoFraudeResponseDto> findAll(
            Pageable pageable,
            String descricao,
            CategoriaFraude codigo,
            Integer severidade);

    TipoFraudeResponseDto findById(UUID id);

    TipoFraudeResponseDto create(
            TipoFraudeRequestDto dto);

    TipoFraudeResponseDto update(
            UUID id,
            TipoFraudeRequestDto dto);

    void delete(UUID id);
}