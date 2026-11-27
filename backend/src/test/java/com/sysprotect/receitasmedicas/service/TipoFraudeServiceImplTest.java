package com.sysprotect.receitasmedicas.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.sysprotect.receitasmedicas.dto.request.TipoFraudeRequestDto;
import com.sysprotect.receitasmedicas.dto.response.TipoFraudeResponseDto;
import com.sysprotect.receitasmedicas.enums.CategoriaFraude;
import com.sysprotect.receitasmedicas.exception.RegistroNaoEncontradoException;
import com.sysprotect.receitasmedicas.mapper.TipoFraudeMapper;
import com.sysprotect.receitasmedicas.model.TipoFraude;
import com.sysprotect.receitasmedicas.repository.TipoFraudeRepository;

@ExtendWith(MockitoExtension.class)
class TipoFraudeServiceImplTest {

    @Mock
    private TipoFraudeRepository tipoFraudeRepository;

    @Mock
    private TipoFraudeMapper tipoFraudeMapper;

    @InjectMocks
    private TipoFraudeServiceImpl tipoFraudeService;

    @Test
    @DisplayName("findAll sem filtros")
    void findAllSemFiltros() {
        Pageable pageable = Pageable.unpaged();
        Page<TipoFraude> page = new PageImpl<>(java.util.List.of());
        when(tipoFraudeRepository.findAll(pageable)).thenReturn(page);
        when(page.map(any())).thenReturn(Page.empty());

        Page<TipoFraudeResponseDto> result = tipoFraudeService.findAll(pageable);

        assertThat(result).isNotNull();
        verify(tipoFraudeRepository).findAll(pageable);
    }

    @Test
    @DisplayName("findAll com filtros preenchidos")
    void findAllComFiltros() {
        Pageable pageable = Pageable.unpaged();
        Page<TipoFraude> page = new PageImpl<>(java.util.List.of());
        when(tipoFraudeRepository.findBy(any(Specification.class), any()))
                .thenReturn(page);
        when(page.map(any())).thenReturn(Page.empty());

        Page<TipoFraudeResponseDto> result = tipoFraudeService.findAll(
                pageable, "fraude", CategoriaFraude.MEDICO_INVALIDO, 3);

        assertThat(result).isNotNull();
        verify(tipoFraudeRepository).findBy(any(Specification.class), any());
    }

    @Test
    @DisplayName("findAll com descricao em branco nao aplica filtro")
    void findAllDescricaoEmBranco() {
        Pageable pageable = Pageable.unpaged();
        Page<TipoFraude> page = new PageImpl<>(java.util.List.of());
        when(tipoFraudeRepository.findBy(any(Specification.class), any()))
                .thenReturn(page);
        when(page.map(any())).thenReturn(Page.empty());

        tipoFraudeService.findAll(pageable, "   ", null, null);

        verify(tipoFraudeRepository).findBy(any(Specification.class), any());
    }

    @Test
    @DisplayName("findById com sucesso")
    void findByIdSucesso() {
        UUID id = UUID.randomUUID();
        TipoFraude tipoFraude = new TipoFraude(id, null, "desc", 1);
        TipoFraudeResponseDto response = new TipoFraudeResponseDto(id, null, "desc", 1);

        when(tipoFraudeRepository.findById(id)).thenReturn(java.util.Optional.of(tipoFraude));
        when(tipoFraudeMapper.toResponse(tipoFraude)).thenReturn(response);

        TipoFraudeResponseDto result = tipoFraudeService.findById(id);

        assertThat(result).isEqualTo(response);
    }

    @Test
    @DisplayName("findById nao encontrado lança excecao")
    void findByIdNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(tipoFraudeRepository.findById(id)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                RegistroNaoEncontradoException.class,
                () -> tipoFraudeService.findById(id));
    }

    @Test
    @DisplayName("create com sucesso")
    void createSucesso() {
        TipoFraudeRequestDto dto = new TipoFraudeRequestDto(
                CategoriaFraude.MEDICO_INVALIDO, "desc", 1);
        TipoFraude tipoFraude = new TipoFraude();
        TipoFraude saved = new TipoFraude(UUID.randomUUID(), null, "desc", 1);
        TipoFraudeResponseDto response = new TipoFraudeResponseDto(
                saved.getId(), null, "desc", 1);

        when(tipoFraudeMapper.toModel(dto)).thenReturn(tipoFraude);
        when(tipoFraudeRepository.save(tipoFraude)).thenReturn(saved);
        when(tipoFraudeMapper.toResponse(saved)).thenReturn(response);

        TipoFraudeResponseDto result = tipoFraudeService.create(dto);

        assertThat(result).isEqualTo(response);
        verify(tipoFraudeRepository).save(tipoFraude);
    }

    @Test
    @DisplayName("update com sucesso")
    void updateSucesso() {
        UUID id = UUID.randomUUID();
        TipoFraudeRequestDto dto = new TipoFraudeRequestDto(
                CategoriaFraude.MEDICO_INVALIDO, "desc atualizada", 2);
        TipoFraude tipoFraude = new TipoFraude(id, null, "desc", 1);
        TipoFraude updated = new TipoFraude(id, null, "desc atualizada", 2);
        TipoFraudeResponseDto response = new TipoFraudeResponseDto(id, null, "desc atualizada", 2);

        when(tipoFraudeRepository.findById(id)).thenReturn(java.util.Optional.of(tipoFraude));
        when(tipoFraudeRepository.save(tipoFraude)).thenReturn(updated);
        when(tipoFraudeMapper.toResponse(updated)).thenReturn(response);

        TipoFraudeResponseDto result = tipoFraudeService.update(id, dto);

        assertThat(result).isEqualTo(response);
        verify(tipoFraudeMapper).updateModel(dto, tipoFraude);
        verify(tipoFraudeRepository).save(tipoFraude);
    }

    @Test
    @DisplayName("update nao encontrado lança excecao")
    void updateNaoEncontrado() {
        UUID id = UUID.randomUUID();
        TipoFraudeRequestDto dto = new TipoFraudeRequestDto(
                CategoriaFraude.MEDICO_INVALIDO, "desc", 1);

        when(tipoFraudeRepository.findById(id)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                RegistroNaoEncontradoException.class,
                () -> tipoFraudeService.update(id, dto));
    }

    @Test
    @DisplayName("delete com sucesso")
    void deleteSucesso() {
        UUID id = UUID.randomUUID();
        TipoFraude tipoFraude = new TipoFraude(id, null, "desc", 1);

        when(tipoFraudeRepository.findById(id)).thenReturn(java.util.Optional.of(tipoFraude));

        tipoFraudeService.delete(id);

        verify(tipoFraudeRepository).delete(tipoFraude);
    }

    @Test
    @DisplayName("delete nao encontrado lança excecao")
    void deleteNaoEncontrado() {
        UUID id = UUID.randomUUID();

        when(tipoFraudeRepository.findById(id)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(
                RegistroNaoEncontradoException.class,
                () -> tipoFraudeService.delete(id));
    }
}
