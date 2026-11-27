package com.sysprotect.receitasmedicas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.sysprotect.receitasmedicas.dto.request.PacienteRequestDto;
import com.sysprotect.receitasmedicas.dto.response.PacienteResponseDto;
import com.sysprotect.receitasmedicas.model.Paciente;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PacienteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "receitas", ignore = true)
    @Mapping(target = "ativo", defaultValue = "true")
    Paciente toModel(PacienteRequestDto dto);

    PacienteResponseDto toResponse(Paciente model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "receitas", ignore = true)
    @Mapping(target = "ativo", defaultValue = "true")
    void updateModel(PacienteRequestDto dto, @MappingTarget Paciente model);
}