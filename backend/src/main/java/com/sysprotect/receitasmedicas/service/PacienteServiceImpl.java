package com.sysprotect.receitasmedicas.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysprotect.receitasmedicas.dto.request.PacienteRequestDto;
import com.sysprotect.receitasmedicas.dto.response.PacienteResponseDto;
import com.sysprotect.receitasmedicas.exception.RegistroNaoEncontradoException;
import com.sysprotect.receitasmedicas.exception.RegraDeNegocioException;
import com.sysprotect.receitasmedicas.mapper.PacienteMapper;
import com.sysprotect.receitasmedicas.model.Paciente;
import com.sysprotect.receitasmedicas.repository.PacienteRepository;

@Service
@Transactional
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;

    public PacienteServiceImpl(
            PacienteRepository pacienteRepository,
            PacienteMapper pacienteMapper) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PacienteResponseDto> findAll(Pageable pageable) {
        return pacienteRepository.findAll(pageable).map(pacienteMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PacienteResponseDto> findAll(Pageable pageable, String nome, String cpf, Boolean ativo) {
        PredicateSpecification<Paciente> filtros = PredicateSpecification.unrestricted();

        if (nome != null && !nome.isBlank()) {
            String filtro = "%" + nome.trim().toLowerCase() + "%";
            filtros = filtros.and((from, builder) ->
                    builder.like(builder.lower(from.get("nome")), filtro));
        }

        if (cpf != null && !cpf.isBlank()) {
            filtros = filtros.and((from, builder) ->
                    builder.equal(from.get("cpf"), cpf.trim()));
        }

        if (ativo != null) {
            filtros = filtros.and((from, builder) ->
                    builder.equal(from.get("ativo"), ativo));
        }

        return pacienteRepository
                .findBy(filtros, query -> query.page(pageable))
                .map(pacienteMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public PacienteResponseDto findById(UUID id) {
        return pacienteMapper.toResponse(findPacienteById(id));
    }

    @Override
    public PacienteResponseDto create(PacienteRequestDto dto) {
        if (pacienteRepository.existsByCpf(dto.cpf())) {
            throw new RegraDeNegocioException("paciente.cpf.duplicado", dto.cpf());
        }
        Paciente paciente = pacienteMapper.toModel(dto);
        return pacienteMapper.toResponse(pacienteRepository.save(paciente));
    }

    @Override
    public PacienteResponseDto update(UUID id, PacienteRequestDto dto) {
        Paciente paciente = findPacienteById(id);
        if (pacienteRepository.existsByCpfAndIdNot(dto.cpf(), id)) {
            throw new RegraDeNegocioException("paciente.cpf.duplicado", dto.cpf());
        }
        pacienteMapper.updateModel(dto, paciente);
        return pacienteMapper.toResponse(pacienteRepository.save(paciente));
    }

    @Override
    public void delete(UUID id) {
        Paciente paciente = findPacienteById(id);
        pacienteRepository.delete(paciente);
    }

    private Paciente findPacienteById(UUID id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("paciente.notfound", id));
    }
}