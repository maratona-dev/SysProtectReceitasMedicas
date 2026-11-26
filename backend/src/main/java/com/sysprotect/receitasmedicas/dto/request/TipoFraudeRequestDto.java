package com.sysprotect.receitasmedicas.dto.request;

import com.sysprotect.receitasmedicas.enums.CategoriaFraude;
import jakarta.validation.constraints.*;

public record TipoFraudeRequestDto(
        @NotNull(message = "A categoria da fraude é obrigatória")
        CategoriaFraude codigo,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String descricao,

        @NotNull(message = "A severidade é obrigatória")
        @Min(value = 1, message = "A severidade mínima é 1")
        @Max(value = 5, message = "A severidade máxima é 5")
        Integer severidade
) {}