package com.sysprotect.receitasmedicas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.sysprotect.receitasmedicas.dto.request.ReceitaRequestDto;
import com.sysprotect.receitasmedicas.dto.response.ReceitaResponse;
import com.sysprotect.receitasmedicas.enums.SituacaoAnalise;
import com.sysprotect.receitasmedicas.model.Receita;

@Mapper(
    componentModel = "spring",
    uses = {
        PacienteMapper.class,
        MedicoMapper.class,
        ReceitaItemMapper.class,
        FraudeMapper.class
    },
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ReceitaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assinaturaValida", ignore = true)
    @Mapping(target = "validadeOk", ignore = true)
    @Mapping(target = "situacaoAnalise", ignore = true)
    @Mapping(target = "paciente", ignore = true)
    @Mapping(target = "medico", ignore = true)
    @Mapping(target = "itens", ignore = true)
    @Mapping(target = "fraudes", ignore = true)
    Receita toModel(ReceitaRequestDto dto);

    @Mapping(target = "situacaoAnalise", source = "situacaoAnalise", qualifiedByName = "codigoToSituacao")
    @Mapping(target = "descricaoSituacao", source = "situacaoAnalise", qualifiedByName = "codigoToDescricaoSituacao")
    ReceitaResponse toResponse(Receita model);

    @Named("codigoToSituacao")
    default SituacaoAnalise codigoToSituacao(Integer codigo) {
        return codigo != null ? SituacaoAnalise.obterPorCodigo(codigo) : SituacaoAnalise.NORMAL;
    }

    @Named("codigoToDescricaoSituacao")
    default String codigoToDescricaoSituacao(Integer codigo) {
        return codigo != null ? SituacaoAnalise.obterNomePorCodigo(codigo) : SituacaoAnalise.NORMAL.getNome();
    }
}