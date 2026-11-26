package com.sysprotect.receitasmedicas.exception;

import com.sysprotect.receitasmedicas.dto.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Should handle UserNotFoundException with 404")
    void shouldHandleUserNotFoundWith404() {
        // Given
        UserNotFoundException ex = new UserNotFoundException("Usuário não encontrado");

        // When
        ResponseEntity<ErrorResponse> response = handler.handleUserNotFound(ex);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getStatus()).isEqualTo(404);
        assertThat(response.getBody().getMessage()).isEqualTo("Usuário não encontrado");
    }

    @Test
    @DisplayName("Should handle InvalidCredentialsException with 401")
    void shouldHandleInvalidCredentialsWith401() {
        // Given
        InvalidCredentialsException ex = new InvalidCredentialsException("Credenciais inválidas");

        // When
        ResponseEntity<ErrorResponse> response = handler.handleInvalidCredentials(ex);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody().getStatus()).isEqualTo(401);
        assertThat(response.getBody().getMessage()).isEqualTo("Credenciais inválidas");
    }

    @Test
    @DisplayName("Should handle UserAlreadyExistsException with 409")
    void shouldHandleUserAlreadyExistsWith409() {
        // Given
        UserAlreadyExistsException ex = new UserAlreadyExistsException("Username já existe");

        // When
        ResponseEntity<ErrorResponse> response = handler.handleUserAlreadyExists(ex);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().getStatus()).isEqualTo(409);
        assertThat(response.getBody().getMessage()).isEqualTo("Username já existe");
    }

    @Test
    @DisplayName("Should handle BadCredentialsException with 401")
    void shouldHandleBadCredentialsWith401() {
        // Given
        BadCredentialsException ex = new BadCredentialsException("Invalid credentials");

        // When
        ResponseEntity<ErrorResponse> response = handler.handleBadCredentials(ex);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody().getMessage()).isEqualTo("Credenciais inválidas");
    }

    @Test
    @DisplayName("Should handle generic Exception with 500")
    void shouldHandleGenericExceptionWith500() {
        // Given
        Exception ex = new RuntimeException("Unexpected error");

        // When
        ResponseEntity<ErrorResponse> response = handler.handleGeneric(ex);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().getStatus()).isEqualTo(500);
        assertThat(response.getBody().getMessage()).isEqualTo("Erro interno do servidor");
    }

    @Test
    @DisplayName("Should handle constraint violation with 400")
    void shouldHandleConstraintViolationWith400() {
        // Given
        jakarta.validation.ConstraintViolationException ex = mock(
            jakarta.validation.ConstraintViolationException.class);
        when(ex.getMessage()).thenReturn("Valor inválido");

        // When
        ResponseEntity<ErrorResponse> response = handler.handleConstraintViolation(ex);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getMessage()).isEqualTo("Valor inválido");
    }
}
