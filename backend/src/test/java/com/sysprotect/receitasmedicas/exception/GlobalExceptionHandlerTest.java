package com.sysprotect.receitasmedicas.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.core.MethodParameter;

import com.sysprotect.receitasmedicas.dto.response.ErroResponseDto;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Test
    @DisplayName("tratarRegistroNaoEncontrado retorna 404")
    void tratarRegistroNaoEncontrado() {
        RegistroNaoEncontradoException ex = new RegistroNaoEncontradoException(
                "tipoFraude.notfound", UUID.randomUUID());
        when(messageSource.getMessage(eq("tipoFraude.notfound"), any(), any(Locale.class)))
                .thenReturn("Tipo fraude nao encontrado");

        ResponseEntity<ErroResponseDto> response = exceptionHandler
                .tratarRegistroNaoEncontrado(ex, Locale.ROOT);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("tratarRegraDeNegocio retorna 400")
    void tratarRegraDeNegocio() {
        RegraDeNegocioException ex = new RegraDeNegocioException(
                "paciente.cpf.duplicado", "123");
        when(messageSource.getMessage(eq("paciente.cpf.duplicado"), any(), any(Locale.class)))
                .thenReturn("CPF duplicado");

        ResponseEntity<ErroResponseDto> response = exceptionHandler
                .tratarRegraDeNegocio(ex, Locale.ROOT);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("tratarErroJsonInvalido retorna 400")
    void tratarErroJsonInvalido() {
        HttpInputMessage inputMessage = mock(HttpInputMessage.class);
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException(
                "JSON invalido", inputMessage);
        when(messageSource.getMessage(eq("error.json.invalid"), any(), any(Locale.class)))
                .thenReturn("JSON invalido");

        ResponseEntity<ErroResponseDto> response = exceptionHandler
                .tratarErroJsonInvalido(ex, Locale.ROOT);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("tratarErroTipoParametro retorna 400")
    void tratarErroTipoParametro() throws Exception {
        MethodParameter parameter = new MethodParameter(
                getClass().getDeclaredMethod("dummyMethod", UUID.class), -1);
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(
                "abc", UUID.class, "id", parameter, null);
        when(messageSource.getMessage(eq("error.parameter.invalid"), any(), any(Locale.class)))
                .thenReturn("Parametro invalido");

        ResponseEntity<ErroResponseDto> response = exceptionHandler
                .tratarErroTipoParametro(ex, Locale.ROOT);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @SuppressWarnings("unused")
    private void dummyMethod(UUID id) {
    }

    @Test
    @DisplayName("tratarMetodoNaoSuportado retorna 405")
    void tratarMetodoNaoSuportado() {
        HttpRequestMethodNotSupportedException ex = new HttpRequestMethodNotSupportedException("DELETE");
        when(messageSource.getMessage(eq("error.method.not.supported"), any(), any(Locale.class)))
                .thenReturn("Metodo nao suportado");

        ResponseEntity<ErroResponseDto> response = exceptionHandler
                .tratarMetodoNaoSuportado(ex, Locale.ROOT);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED.value());
    }

    @Test
    @DisplayName("tratarViolacaoIntegridade retorna 409")
    void tratarViolacaoIntegridade() {
        DataIntegrityViolationException ex = new DataIntegrityViolationException("violation");
        when(messageSource.getMessage(eq("error.data.integrity"), any(), any(Locale.class)))
                .thenReturn("Integridade violada");

        ResponseEntity<ErroResponseDto> response = exceptionHandler
                .tratarViolacaoIntegridade(ex, Locale.ROOT);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.CONFLICT.value());
    }

    @Test
    @DisplayName("tratarErroInesperado retorna 500")
    void tratarErroInesperado() {
        Exception ex = new Exception("erro inesperado");
        when(messageSource.getMessage(eq("error.internal"), any(), any(Locale.class)))
                .thenReturn("Erro interno");

        ResponseEntity<ErroResponseDto> response = exceptionHandler
                .tratarErroInesperado(ex, Locale.ROOT);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
