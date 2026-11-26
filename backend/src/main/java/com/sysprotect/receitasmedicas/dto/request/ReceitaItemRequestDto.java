package com.sysprotect.receitasmedicas.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public record ReceitaItemRequestDto(
        @NotNull(message = "O ID do medicamento é obrigatório")
        UUID medicamentoId,

        @NotNull(message = "A dosagem prescrita é obrigatória")
        @Positive(message = "A dosagem prescrita deve ser maior que zero")
        BigDecimal dosagemPrescrita,

        @NotNull(message = "A quantidade é obrigatória")
        @Min(value = 1, message = "A quantidade mínima deve ser 1")
        Integer quantidade,

        @Size(max = 255, message = "A posologia deve ter no máximo 255 caracteres")
        String posologia
) {}