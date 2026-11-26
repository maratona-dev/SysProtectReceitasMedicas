package com.sysprotect.receitasmedicas.security;

import com.sysprotect.receitasmedicas.config.JwtProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class JwtTokenProviderTest {

    private JwtProperties jwtProperties;
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtProperties = mock(JwtProperties.class);
        when(jwtProperties.getSecret()).thenReturn("test-secret-key-for-testing-purposes-only-123");
        when(jwtProperties.getExpirationMs()).thenReturn(86400000L);
        when(jwtProperties.getRefreshExpirationMs()).thenReturn(2592000000L);
        jwtTokenProvider = new JwtTokenProvider(jwtProperties);
    }

    @Test
    @DisplayName("Should create access token with username and role")
    void shouldCreateAccessTokenWithUsernameAndRole() {
        // When
        String token = jwtTokenProvider.createToken("testuser", "USER");

        // Then
        assertThat(token).isNotNull();
        assertThat(token).isNotBlank();
        assertThat(jwtTokenProvider.getUsername(token)).isEqualTo("testuser");
        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    @DisplayName("Should create refresh token")
    void shouldCreateRefreshToken() {
        // When
        String token = jwtTokenProvider.createRefreshToken("testuser");

        // Then
        assertThat(token).isNotNull();
        assertThat(token).isNotBlank();
        assertThat(jwtTokenProvider.getUsername(token)).isEqualTo("testuser");
        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    @DisplayName("Should validate a valid token")
    void shouldValidateValidToken() {
        // Given
        String token = jwtTokenProvider.createToken("testuser", "USER");

        // When
        boolean valid = jwtTokenProvider.validateToken(token);

        // Then
        assertThat(valid).isTrue();
    }

    @Test
    @DisplayName("Should return false for null token")
    void shouldReturnFalseForNullToken() {
        // When
        boolean valid = jwtTokenProvider.validateToken(null);

        // Then
        assertThat(valid).isFalse();
    }

    @Test
    @DisplayName("Should return false for blank token")
    void shouldReturnFalseForBlankToken() {
        // When
        boolean valid = jwtTokenProvider.validateToken("");

        // Then
        assertThat(valid).isFalse();
    }

    @Test
    @DisplayName("Should return false for whitespace-only token")
    void shouldReturnFalseForWhitespaceToken() {
        // When
        boolean valid = jwtTokenProvider.validateToken("   ");

        // Then
        assertThat(valid).isFalse();
    }

    @Test
    @DisplayName("Should return false for invalid token")
    void shouldReturnFalseForInvalidToken() {
        // When
        boolean valid = jwtTokenProvider.validateToken("invalid.token.here");

        // Then
        assertThat(valid).isFalse();
    }

    @Test
    @DisplayName("Should return false for token signed with different secret")
    void shouldReturnFalseForTokenWithDifferentSecret() {
        // Given
        JwtProperties differentProps = mock(JwtProperties.class);
        when(differentProps.getSecret()).thenReturn("different-secret-key");
        when(differentProps.getExpirationMs()).thenReturn(86400000L);
        JwtTokenProvider differentProvider = new JwtTokenProvider(differentProps);
        String token = differentProvider.createToken("testuser", "USER");

        // When
        boolean valid = jwtTokenProvider.validateToken(token);

        // Then
        assertThat(valid).isFalse();
    }

    @Test
    @DisplayName("Should extract username from token")
    void shouldExtractUsernameFromToken() {
        // Given
        String token = jwtTokenProvider.createToken("myuser", "ADMIN");

        // When
        String username = jwtTokenProvider.getUsername(token);

        // Then
        assertThat(username).isEqualTo("myuser");
    }

    @Test
    @DisplayName("Should resolve token from Authorization header")
    void shouldResolveTokenFromAuthorizationHeader() {
        // Given
        jakarta.servlet.http.HttpServletRequest request = mock(jakarta.servlet.http.HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer eyJhbGci");

        // When
        String token = jwtTokenProvider.resolveToken(request);

        // Then
        assertThat(token).isEqualTo("eyJhbGci");
    }

    @Test
    @DisplayName("Should return null when no Authorization header")
    void shouldReturnNullWhenNoAuthorizationHeader() {
        // Given
        jakarta.servlet.http.HttpServletRequest request = mock(jakarta.servlet.http.HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(null);

        // When
        String token = jwtTokenProvider.resolveToken(request);

        // Then
        assertThat(token).isNull();
    }

    @Test
    @DisplayName("Should return null when Authorization header does not start with Bearer")
    void shouldReturnNullWhenHeaderDoesNotStartWithBearer() {
        // Given
        jakarta.servlet.http.HttpServletRequest request = mock(jakarta.servlet.http.HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Basic somecredentials");

        // When
        String token = jwtTokenProvider.resolveToken(request);

        // Then
        assertThat(token).isNull();
    }
}
