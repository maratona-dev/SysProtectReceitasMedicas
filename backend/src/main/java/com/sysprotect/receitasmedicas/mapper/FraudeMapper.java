package com.sysprotect.receitasmedicas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.sysprotect.receitasmedicas.dto.response.FraudeResponseDto;
import com.sysprotect.receitasmedicas.model.Fraude;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FraudeMapper {

    @Mapping(target = "receitaId", source = "receita.id")
    @Mapping(target = "tipoFraude", source = "tipoFraude.codigo")
    @Mapping(target = "descricaoTipoFraude", source = "tipoFraude.descricao")
    @Mapping(target = "severidade", source = "tipoFraude.severidade")
    FraudeResponseDto toResponse(Fraude model);
}