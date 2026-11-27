package com.sysprotect.receitasmedicas.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sysprotect.receitasmedicas.dto.request.MedicoRequestDto;
import com.sysprotect.receitasmedicas.dto.response.MedicoResponseDto;

public interface MedicoService {

    Page<MedicoResponseDto> findAll(Pageable pageable);

    Page<MedicoResponseDto> findAll(Pageable pageable, String nome, String crm, String ufCrm, Boolean crmAtivo);

    MedicoResponseDto findById(UUID id);

    MedicoResponseDto create(MedicoRequestDto dto);

    MedicoResponseDto update(UUID id, MedicoRequestDto dto);

    void delete(UUID id);
}