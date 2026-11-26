package com.sysprotect.receitasmedicas.dto.request;

import jakarta.validation.constraints.*;

public record MedicoRequestDto(
        @NotBlank(message = "O nome do médico é obrigatório")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
        String nome,

        @NotBlank(message = "O CRM é obrigatório")
        @Size(max = 20, message = "O CRM deve ter no máximo 20 caracteres")
        String crm,

        @NotBlank(message = "A UF do CRM é obrigatória")
        @Pattern(regexp = "[A-Z]{2}", message = "A UF deve conter 2 letras maiúsculas")
        String ufCrm,

        @Size(max = 100, message = "A especialidade deve ter no máximo 100 caracteres")
        String especialidade,

        Boolean crmAtivo
) {}