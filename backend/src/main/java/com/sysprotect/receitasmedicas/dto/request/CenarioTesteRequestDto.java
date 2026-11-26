package com.sysprotect.receitasmedicas.dto.request;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record CenarioTesteRequestDto(
        @NotNull(message = "O ID da condição de teste é obrigatório")
        UUID condicaoTesteId,

        @NotBlank(message = "O nome do cenário é obrigatório")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
        String nome,

        @NotBlank(message = "A entrada simulada é obrigatória")
        @Size(max = 255, message = "A entrada simulada deve ter no máximo 255 caracteres")
        String entradaSimulada,

        @NotNull(message = "A indicação de fraude esperada é obrigatória")
        Boolean fraudeEsperada
) {}