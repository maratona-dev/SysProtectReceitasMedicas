package com.sysprotect.receitasmedicas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.sysprotect.receitasmedicas.dto.request.CondicaoTesteRequestDto;
import com.sysprotect.receitasmedicas.dto.response.CondicaoTesteResponseDto;
import com.sysprotect.receitasmedicas.model.CondicaoTeste;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CondicaoTesteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoFraude", ignore = true)
    @Mapping(target = "cenariosTeste", ignore = true)
    @Mapping(target = "ativa", defaultValue = "true")
    CondicaoTeste toModel(CondicaoTesteRequestDto dto);

    @Mapping(target = "tipoFraudeId", source = "tipoFraude.id")
    @Mapping(target = "tipoFraudeCodigo", source = "tipoFraude.codigo")
    CondicaoTesteResponseDto toResponse(CondicaoTeste model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoFraude", ignore = true)
    @Mapping(target = "cenariosTeste", ignore = true)
    @Mapping(target = "ativa", defaultValue = "true")
    void updateModel(CondicaoTesteRequestDto dto, @MappingTarget CondicaoTeste model);
}