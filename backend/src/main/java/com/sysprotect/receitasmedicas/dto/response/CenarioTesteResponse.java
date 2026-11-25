package com.sysprotect.receitasmedicas.dto.response;

import java.util.UUID;

public record CenarioTesteResponse(
        UUID id,
        UUID condicaoTesteId,
        String nome,
        String entradaSimulada,
        Boolean fraudeEsperada
) {}