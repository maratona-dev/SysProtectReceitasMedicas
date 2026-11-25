package com.sysprotect.receitasmedicas.dto.request;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record PacienteRequest(
        @NotBlank(message = "O nome do paciente é obrigatório")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
        String nome,

        @NotBlank(message = "O CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos numéricos")
        String cpf,

        @NotNull(message = "A data de nascimento é obrigatória")
        @Past(message = "A data de nascimento deve ser no passado")
        LocalDate dataNascimento,

        Boolean ativo
) {}