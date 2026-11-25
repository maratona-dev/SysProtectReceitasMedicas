package com.sysprotect.receitasmedicas.dto.response;

import com.sysprotect.receitasmedicas.enums.CategoriaFraude;
import java.util.UUID;

public record TipoFraudeResponse(
        UUID id,
        CategoriaFraude codigo,
        String descricao,
        Integer severidade
) {}