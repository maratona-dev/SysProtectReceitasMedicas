package com.sysprotect.receitasmedicas.dto.request;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record CondicaoTesteRequest(
        @NotNull(message = "O ID do tipo de fraude é obrigatório")
        UUID tipoFraudeId,

        @NotBlank(message = "O campo alvo é obrigatório")
        @Size(max = 100, message = "O campo alvo deve ter no máximo 100 caracteres")
        String campoAlvo,

        @NotBlank(message = "O operador é obrigatório")
        @Size(max = 20, message = "O operador deve ter no máximo 20 caracteres")
        String operador,

        @NotBlank(message = "O valor esperado é obrigatório")
        @Size(max = 100, message = "O valor esperado deve ter no máximo 100 caracteres")
        String valorEsperado,

        Boolean ativa
) {}