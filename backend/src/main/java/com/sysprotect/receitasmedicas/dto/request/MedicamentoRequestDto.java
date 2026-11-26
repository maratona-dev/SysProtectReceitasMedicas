package com.sysprotect.receitasmedicas.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record MedicamentoRequestDto(
        @NotBlank(message = "O nome do medicamento é obrigatório")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
        String nome,

        @Size(max = 30, message = "O código ANVISA deve ter no máximo 30 caracteres")
        String codigoAnvisa,

        @NotBlank(message = "O princípio ativo é obrigatório")
        @Size(max = 150, message = "O princípio ativo deve ter no máximo 150 caracteres")
        String principioAtivo,

        @Positive(message = "A dosagem máxima diária deve ser maior que zero")
        BigDecimal dosagemMaximaDiaria,

        @Size(max = 20, message = "A unidade de medida deve ter no máximo 20 caracteres")
        String unidadeMedida,

        Boolean controlado,

        Boolean ativo
) {}