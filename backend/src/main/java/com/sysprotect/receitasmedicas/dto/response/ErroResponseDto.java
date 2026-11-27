package com.sysprotect.receitasmedicas.dto.response;

import java.time.Instant;

public record ErroResponseDto(
        Integer status, 
        String erro,
        String mensagem,        
        Instant dataHora
) {
}