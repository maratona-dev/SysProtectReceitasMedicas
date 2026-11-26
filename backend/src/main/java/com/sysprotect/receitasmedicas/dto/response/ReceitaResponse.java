package com.sysprotect.receitasmedicas.dto.response;

import com.sysprotect.receitasmedicas.enums.SituacaoAnalise;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReceitaResponse(
        UUID id,
        String codigoReceita,
        String hashConteudo,
        LocalDateTime dataEmissao,
        LocalDate dataValidade,
        Boolean assinaturaValida,
        Boolean validadeOk,
        SituacaoAnalise situacaoAnalise,
        String descricaoSituacao,
        PacienteResponseDto paciente,
        MedicoResponseDto medico,
        List<ReceitaItemResponseDto> itens,
        List<FraudeResponseDto> fraudes
) {}