package com.sysprotect.receitasmedicas.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ReceitaItemResponse(
        UUID id,
        UUID medicamentoId,
        String medicamentoNome,
        BigDecimal dosagemPrescrita,
        Integer quantidade,
        String posologia
) {}