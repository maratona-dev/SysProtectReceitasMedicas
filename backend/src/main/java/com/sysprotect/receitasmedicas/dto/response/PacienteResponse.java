package com.sysprotect.receitasmedicas.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record PacienteResponse(
        UUID id,
        String nome,
        String cpf,
        LocalDate dataNascimento,
        Boolean ativo
) {}