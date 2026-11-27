
package com.sysprotect.receitasmedicas.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysprotect.receitasmedicas.dto.request.TipoFraudeRequestDto;
import com.sysprotect.receitasmedicas.dto.response.TipoFraudeResponseDto;
import com.sysprotect.receitasmedicas.enums.CategoriaFraude;
import com.sysprotect.receitasmedicas.exception.RegistroNaoEncontradoException;
import com.sysprotect.receitasmedicas.mapper.TipoFraudeMapper;
import com.sysprotect.receitasmedicas.model.TipoFraude;
import com.sysprotect.receitasmedicas.repository.TipoFraudeRepository;

@Service
@Transactional
public class TipoFraudeServiceImpl implements TipoFraudeService {

    private final TipoFraudeRepository tipoFraudeRepository;
    private final TipoFraudeMapper tipoFraudeMapper;

    public TipoFraudeServiceImpl(
            TipoFraudeRepository tipoFraudeRepository,
            TipoFraudeMapper tipoFraudeMapper) {

        this.tipoFraudeRepository = tipoFraudeRepository;
        this.tipoFraudeMapper = tipoFraudeMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoFraudeResponseDto> findAll(Pageable pageable) {

        return tipoFraudeRepository
                .findAll(pageable)
                .map(tipoFraudeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoFraudeResponseDto> findAll(
            Pageable pageable,
            String descricao,
            CategoriaFraude codigo,
            Integer severidade) {

        PredicateSpecification<TipoFraude> filtros =
                PredicateSpecification.unrestricted();

        if (descricao != null && !descricao.isBlank()) {

            String descricaoFiltro = descricao.trim().toLowerCase();

            filtros = filtros.and((from, builder) ->
                    builder.like(
                            builder.lower(from.get("descricao")),
                            "%" + descricaoFiltro + "%"
                    )
            );
        }

        if (codigo != null) {

            filtros = filtros.and((from, builder) ->
                    builder.equal(from.get("codigo"), codigo)
            );
        }

        if (severidade != null) {

            filtros = filtros.and((from, builder) ->
                    builder.equal(from.get("severidade"), severidade)
            );
        }

        return tipoFraudeRepository
                .findBy(filtros, query -> query.page(pageable))
                .map(tipoFraudeMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoFraudeResponseDto findById(UUID id) {

        TipoFraude tipoFraude = findTipoFraudeById(id);

        return tipoFraudeMapper.toResponse(tipoFraude);
    }

    @Override
    public TipoFraudeResponseDto create(TipoFraudeRequestDto dto) {

        TipoFraude tipoFraude = tipoFraudeMapper.toModel(dto);

        TipoFraude savedTipoFraude =
                tipoFraudeRepository.save(tipoFraude);

        return tipoFraudeMapper.toResponse(savedTipoFraude);
    }

    @Override
    public TipoFraudeResponseDto update(
            UUID id,
            TipoFraudeRequestDto dto) {

        TipoFraude tipoFraude = findTipoFraudeById(id);

        tipoFraudeMapper.updateModel(dto, tipoFraude);

        TipoFraude updatedTipoFraude =
                tipoFraudeRepository.save(tipoFraude);

        return tipoFraudeMapper.toResponse(updatedTipoFraude);
    }

    @Override
    public void delete(UUID id) {

        TipoFraude tipoFraude = findTipoFraudeById(id);

        tipoFraudeRepository.delete(tipoFraude);
    }

    private TipoFraude findTipoFraudeById(UUID id) {

        return tipoFraudeRepository
                .findById(id)
                .orElseThrow(() ->
                        new RegistroNaoEncontradoException(
                                "tipoFraude.notfound",
                                id));
    }
}
