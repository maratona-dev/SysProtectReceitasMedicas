package com.sysprotect.receitasmedicas.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReceitaRequestDto(
        @NotBlank(message = "O código da receita é obrigatório")
        @Size(max = 64, message = "O código da receita deve ter no máximo 64 caracteres")
        String codigoReceita,

        @NotBlank(message = "O hash do conteúdo é obrigatório")
        @Size(max = 128, message = "O hash deve ter no máximo 128 caracteres")
        String hashConteudo,

        @NotNull(message = "A data de emissão é obrigatória")
        LocalDateTime dataEmissao,

        @NotNull(message = "A data de validade é obrigatória")
        LocalDate dataValidade,

        String assinaturaDigital,

        @NotNull(message = "O ID do paciente é obrigatório")
        UUID pacienteId,

        @NotNull(message = "O ID do médico é obrigatório")
        UUID medicoId,

        @NotEmpty(message = "A receita deve conter pelo menos um medicamento")
        @Valid
        List<ReceitaItemRequestDto> itens
) {}