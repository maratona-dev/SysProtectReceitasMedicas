package com.sysprotect.receitasmedicas.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.data.jpa.domain.Specification;

import com.sysprotect.receitasmedicas.dto.request.PacienteRequestDto;
import com.sysprotect.receitasmedicas.dto.response.PacienteResponseDto;
import com.sysprotect.receitasmedicas.exception.RegistroNaoEncontradoException;
import com.sysprotect.receitasmedicas.exception.RegraDeNegocioException;
import com.sysprotect.receitasmedicas.mapper.PacienteMapper;
import com.sysprotect.receitasmedicas.model.Paciente;
import com.sysprotect.receitasmedicas.repository.PacienteRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PacienteServiceImplTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private PacienteMapper pacienteMapper;

    @InjectMocks
    private PacienteServiceImpl pacienteService;

    @Test
    @DisplayName("findAll sem filtros")
    void findAllSemFiltros() {
        Pageable pageable = Pageable.unpaged();
        Page<Paciente> page = new PageImpl<>(java.util.List.of());
        when(pacienteRepository.findAll(pageable)).thenReturn(page);

        Page<PacienteResponseDto> result = pacienteService.findAll(pageable);

        assertThat(result).isNotNull();
        verify(pacienteRepository).findAll(pageable);
    }

    @Test
    @DisplayName("findAll com filtros preenchidos")
    void findAllComFiltros() {
        Pageable pageable = Pageable.unpaged();
        Page<Paciente> page = new PageImpl<>(java.util.List.of());
        doReturn(page).when(pacienteRepository).findBy(any(PredicateSpecification.class), any());

        Page<PacienteResponseDto> result = pacienteService.findAll(
                pageable, "Joao", "12345678901", true);

        assertThat(result).isNotNull();
        verify(pacienteRepository).findBy(any(PredicateSpecification.class), any());
    }

    @Test
    @DisplayName("findAll com nome em branco nao aplica filtro de nome")
    void findAllNomeEmBranco() {
        Pageable pageable = Pageable.unpaged();
        Page<Paciente> page = new PageImpl<>(java.util.List.of());
        doReturn(page).when(pacienteRepository).findBy(any(PredicateSpecification.class), any());

        pacienteService.findAll(pageable, "   ", null, null);

        verify(pacienteRepository).findBy(any(PredicateSpecification.class), any());
    }

    @Test
    @DisplayName("findById com sucesso")
    void findByIdSucesso() {
        UUID id = UUID.randomUUID();
        Paciente paciente = new Paciente(id, "Joao", "12345678901", LocalDate.of(1990, 1, 1), true);
        PacienteResponseDto response = new PacienteResponseDto(id, "Joao", "12345678901",
                LocalDate.of(1990, 1, 1), true);

        when(pacienteRepository.findById(id)).thenReturn(java.util.Optional.of(paciente));
        when(pacienteMapper.toResponse(paciente)).thenReturn(response);

        PacienteResponseDto result = pacienteService.findById(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    @DisplayName("findById nao encontrado lança excecao")
    void findByIdNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(pacienteRepository.findById(id)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                RegistroNaoEncontradoException.class,
                () -> pacienteService.findById(id));
    }

    @Test
    @DisplayName("create com sucesso")
    void createSucesso() {
        PacienteRequestDto dto = new PacienteRequestDto("Joao", "12345678901",
                LocalDate.of(1990, 1, 1), true);
        Paciente paciente = new Paciente();
        Paciente saved = new Paciente(UUID.randomUUID(), "Joao", "12345678901",
                LocalDate.of(1990, 1, 1), true);
        PacienteResponseDto response = new PacienteResponseDto(saved.getId(), "Joao",
                "12345678901", LocalDate.of(1990, 1, 1), true);

        when(pacienteRepository.existsByCpf("12345678901")).thenReturn(false);
        when(pacienteMapper.toModel(dto)).thenReturn(paciente);
        when(pacienteRepository.save(paciente)).thenReturn(saved);
        when(pacienteMapper.toResponse(saved)).thenReturn(response);

        PacienteResponseDto result = pacienteService.create(dto);

        assertThat(result).isEqualTo(response);
        verify(pacienteRepository).existsByCpf("12345678901");
        verify(pacienteRepository).save(paciente);
    }

    @Test
    @DisplayName("create com cpf duplicado lança excecao")
    void createCpfDuplicado() {
        PacienteRequestDto dto = new PacienteRequestDto("Joao", "12345678901",
                LocalDate.of(1990, 1, 1), true);

        when(pacienteRepository.existsByCpf("12345678901")).thenReturn(true);

        org.junit.jupiter.api.Assertions.assertThrows(
                RegraDeNegocioException.class,
                () -> pacienteService.create(dto));

        verify(pacienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("update com sucesso")
    void updateSucesso() {
        UUID id = UUID.randomUUID();
        PacienteRequestDto dto = new PacienteRequestDto("Joao Atualizado", "12345678901",
                LocalDate.of(1990, 1, 1), true);
        Paciente paciente = new Paciente(id, "Joao", "12345678901", LocalDate.of(1990, 1, 1), true);
        Paciente updated = new Paciente(id, "Joao Atualizado", "12345678901",
                LocalDate.of(1990, 1, 1), true);
        PacienteResponseDto response = new PacienteResponseDto(id, "Joao Atualizado",
                "12345678901", LocalDate.of(1990, 1, 1), true);

        when(pacienteRepository.findById(id)).thenReturn(java.util.Optional.of(paciente));
        when(pacienteRepository.existsByCpfAndIdNot("12345678901", id)).thenReturn(false);
        when(pacienteRepository.save(paciente)).thenReturn(updated);
        when(pacienteMapper.toResponse(updated)).thenReturn(response);

        PacienteResponseDto result = pacienteService.update(id, dto);

        assertThat(result).isEqualTo(response);
        verify(pacienteMapper).updateModel(dto, paciente);
        verify(pacienteRepository).save(paciente);
    }

    @Test
    @DisplayName("update com cpf duplicado para outro id lança excecao")
    void updateCpfDuplicado() {
        UUID id = UUID.randomUUID();
        PacienteRequestDto dto = new PacienteRequestDto("Joao", "99999999999",
                LocalDate.of(1990, 1, 1), true);
        Paciente paciente = new Paciente(id, "Joao", "12345678901", LocalDate.of(1990, 1, 1), true);

        when(pacienteRepository.findById(id)).thenReturn(java.util.Optional.of(paciente));
        when(pacienteRepository.existsByCpfAndIdNot("99999999999", id)).thenReturn(true);

        org.junit.jupiter.api.Assertions.assertThrows(
                RegraDeNegocioException.class,
                () -> pacienteService.update(id, dto));

        verify(pacienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("update nao encontrado lança excecao")
    void updateNaoEncontrado() {
        UUID id = UUID.randomUUID();
        PacienteRequestDto dto = new PacienteRequestDto("Joao", "12345678901",
                LocalDate.of(1990, 1, 1), true);

        when(pacienteRepository.findById(id)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                RegistroNaoEncontradoException.class,
                () -> pacienteService.update(id, dto));
    }

    @Test
    @DisplayName("delete com sucesso")
    void deleteSucesso() {
        UUID id = UUID.randomUUID();
        Paciente paciente = new Paciente(id, "Joao", "12345678901", LocalDate.of(1990, 1, 1), true);

        when(pacienteRepository.findById(id)).thenReturn(java.util.Optional.of(paciente));

        pacienteService.delete(id);

        verify(pacienteRepository).delete(paciente);
    }

    @Test
    @DisplayName("delete nao encontrado lança excecao")
    void deleteNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(pacienteRepository.findById(id)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                RegistroNaoEncontradoException.class,
                () -> pacienteService.delete(id));
    }
}
