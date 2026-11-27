package com.sysprotect.receitasmedicas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.sysprotect.receitasmedicas.dto.request.MedicamentoRequestDto;
import com.sysprotect.receitasmedicas.dto.response.MedicamentoResponseDto;
import com.sysprotect.receitasmedicas.model.Medicamento;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MedicamentoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "controlado", defaultValue = "false")
    @Mapping(target = "ativo", defaultValue = "true")
    Medicamento toModel(MedicamentoRequestDto dto);

    MedicamentoResponseDto toResponse(Medicamento model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "controlado", defaultValue = "false")
    @Mapping(target = "ativo", defaultValue = "true")
    void updateModel(MedicamentoRequestDto dto, @MappingTarget Medicamento model);
}