package com.sysprotect.receitasmedicas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.sysprotect.receitasmedicas.dto.request.CenarioTesteRequestDto;
import com.sysprotect.receitasmedicas.dto.response.CenarioTesteResponseDto;
import com.sysprotect.receitasmedicas.model.CenarioTeste;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CenarioTesteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "condicaoTeste", ignore = true)
    CenarioTeste toModel(CenarioTesteRequestDto dto);

    @Mapping(target = "condicaoTesteId", source = "condicaoTeste.id")
    CenarioTesteResponseDto toResponse(CenarioTeste model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "condicaoTeste", ignore = true)
    void updateModel(CenarioTesteRequestDto dto, @MappingTarget CenarioTeste model);
}