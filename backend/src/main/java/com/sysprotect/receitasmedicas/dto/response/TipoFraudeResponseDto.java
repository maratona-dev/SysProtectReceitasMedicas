package com.sysprotect.receitasmedicas.dto.response;

import com.sysprotect.receitasmedicas.enums.CategoriaFraude;
import java.util.UUID;

public record TipoFraudeResponseDto(
        UUID id,
        CategoriaFraude codigo,
        String descricao,
        Integer severidade
) {}