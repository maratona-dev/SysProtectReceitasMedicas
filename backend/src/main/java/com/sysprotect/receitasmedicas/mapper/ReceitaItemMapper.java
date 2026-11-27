package com.sysprotect.receitasmedicas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.sysprotect.receitasmedicas.dto.request.ReceitaItemRequestDto;
import com.sysprotect.receitasmedicas.dto.response.ReceitaItemResponseDto;
import com.sysprotect.receitasmedicas.model.ReceitaItem;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ReceitaItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "receita", ignore = true)
    @Mapping(target = "medicamento", ignore = true)
    ReceitaItem toModel(ReceitaItemRequestDto dto);

    @Mapping(target = "medicamentoId", source = "medicamento.id")
    @Mapping(target = "medicamentoNome", source = "medicamento.nome")
    ReceitaItemResponseDto toResponse(ReceitaItem model);
}