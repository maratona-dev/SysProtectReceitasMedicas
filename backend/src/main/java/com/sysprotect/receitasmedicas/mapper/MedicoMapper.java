package com.sysprotect.receitasmedicas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.sysprotect.receitasmedicas.dto.request.MedicoRequestDto;
import com.sysprotect.receitasmedicas.dto.response.MedicoResponseDto;
import com.sysprotect.receitasmedicas.model.Medico;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MedicoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "receitas", ignore = true)
    @Mapping(target = "crmAtivo", defaultValue = "true")
    Medico toModel(MedicoRequestDto dto);

    MedicoResponseDto toResponse(Medico model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "receitas", ignore = true)
    @Mapping(target = "crmAtivo", defaultValue = "true")
    void updateModel(MedicoRequestDto dto, @MappingTarget Medico model);
}