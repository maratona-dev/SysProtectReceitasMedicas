
package com.sysprotect.receitasmedicas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.sysprotect.receitasmedicas.dto.request.TipoFraudeRequestDto;
import com.sysprotect.receitasmedicas.dto.response.TipoFraudeResponseDto;
import com.sysprotect.receitasmedicas.model.TipoFraude;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TipoFraudeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fraudes", ignore = true)
    @Mapping(target = "condicoesTeste", ignore = true)
    TipoFraude toModel(TipoFraudeRequestDto dto);

    TipoFraudeResponseDto toResponse(TipoFraude model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fraudes", ignore = true)
    @Mapping(target = "condicoesTeste", ignore = true)
    void updateModel(
            TipoFraudeRequestDto dto,
            @MappingTarget TipoFraude model);
}
