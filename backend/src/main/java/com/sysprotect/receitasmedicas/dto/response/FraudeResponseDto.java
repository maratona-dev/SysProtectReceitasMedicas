package com.sysprotect.receitasmedicas.dto.response;

import com.sysprotect.receitasmedicas.enums.CategoriaFraude;
import java.time.LocalDateTime;
import java.util.UUID;

public record FraudeResponseDto(
        UUID id,
        UUID receitaId,
        CategoriaFraude tipoFraude,
        String descricaoTipoFraude,
        Integer severidade,
        LocalDateTime dataDeteccao,
        String detalhesEvidencia,
        Boolean resolvida
) {}