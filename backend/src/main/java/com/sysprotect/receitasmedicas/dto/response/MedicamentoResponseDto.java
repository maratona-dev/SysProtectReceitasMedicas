package com.sysprotect.receitasmedicas.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record MedicamentoResponseDto(
        UUID id,
        String nome,
        String codigoAnvisa,
        String principioAtivo,
        BigDecimal dosagemMaximaDiaria,
        String unidadeMedida,
        Boolean controlado,
        Boolean ativo
) {}