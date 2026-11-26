package com.sysprotect.receitasmedicas.dto.response;

import java.util.UUID;

public record MedicoResponseDto(
        UUID id,
        String nome,
        String crm,
        String ufCrm,
        String especialidade,
        Boolean crmAtivo
) {}