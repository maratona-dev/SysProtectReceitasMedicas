package com.sysprotect.receitasmedicas.dto.response;

import com.sysprotect.receitasmedicas.enums.CategoriaFraude;
import java.util.UUID;

public record CondicaoTesteResponse(
        UUID id,
        UUID tipoFraudeId,
        CategoriaFraude tipoFraudeCodigo,
        String campoAlvo,
        String operador,
        String valorEsperado,
        Boolean ativa
) {}