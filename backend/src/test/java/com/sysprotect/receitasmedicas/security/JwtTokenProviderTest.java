package com.sysprotect.receitasmedicas.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sysprotect.receitasmedicas.config.JwtProperties;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @Test
    @DisplayName("createToken e createRefreshToken geram tokens nao vazios")
    void createTokensTest() {
        JwtProperties props = new JwtProperties();
        props.setSecret("minha-chave-secreta-de-teste-123");
        props.setExpirationMs(3600000L);
        props.setRefreshExpirationMs(86400000L);

        JwtTokenProvider provider = new JwtTokenProvider(props);

        String token = provider.createToken("usuario", "ADMIN");
        assertThat(token).isNotBlank();

        String refreshToken = provider.createRefreshToken("usuario");
        assertThat(refreshToken).isNotBlank();
    }

    @Test
    @DisplayName("validateToken - token valido retorna true")
    void validateTokenValido() {
        JwtProperties props = new JwtProperties();
        props.setSecret("minha-chave-secreta-de-teste-123");
        props.setExpirationMs(3600000L);
        props.setRefreshExpirationMs(86400000L);

        JwtTokenProvider provider = new JwtTokenProvider(props);
        String token = provider.createToken("usuario", "ADMIN");

        assertThat(provider.validateToken(token)).isTrue();
    }

    @Test
    @DisplayName("validateToken - token nulo ou branco retorna false")
    void validateTokenNuloOuBranco() {
        JwtProperties props = new JwtProperties();
        props.setSecret("minha-chave-secreta-de-teste-123");
        props.setExpirationMs(3600000L);
        props.setRefreshExpirationMs(86400000L);

        JwtTokenProvider provider = new JwtTokenProvider(props);

        assertThat(provider.validateToken(null)).isFalse();
        assertThat(provider.validateToken("")).isFalse();
        assertThat(provider.validateToken("   ")).isFalse();
    }

    @Test
    @DisplayName("validateToken - token invalido retorna false")
    void validateTokenInvalido() {
        JwtProperties props = new JwtProperties();
        props.setSecret("minha-chave-secreta-de-teste-123");
        props.setExpirationMs(3600000L);
        props.setRefreshExpirationMs(86400000L);

        JwtTokenProvider provider = new JwtTokenProvider(props);

        assertThat(provider.validateToken("token.invalido.qualquercoisa")).isFalse();
    }

    @Test
    @DisplayName("getUsername extrai o subject do token")
    void getUsernameTest() {
        JwtProperties props = new JwtProperties();
        props.setSecret("minha-chave-secreta-de-teste-123");
        props.setExpirationMs(3600000L);
        props.setRefreshExpirationMs(86400000L);

        JwtTokenProvider provider = new JwtTokenProvider(props);
        String token = provider.createToken("meuusuario", "ADMIN");

        assertThat(provider.getUsername(token)).isEqualTo("meuusuario");
    }

    @Test
    @DisplayName("resolveToken - extrai Bearer e retorna null quando ausente")
    void resolveTokenTest() {
        JwtProperties props = new JwtProperties();
        props.setSecret("minha-chave-secreta-de-teste-123");
        props.setExpirationMs(3600000L);
        props.setRefreshExpirationMs(86400000L);

        JwtTokenProvider provider = new JwtTokenProvider(props);

        HttpServletRequest requestComBearer = mock(HttpServletRequest.class);
        when(requestComBearer.getHeader("Authorization")).thenReturn("Bearer abc123token");
        assertThat(provider.resolveToken(requestComBearer)).isEqualTo("abc123token");

        HttpServletRequest requestSemToken = mock(HttpServletRequest.class);
        when(requestSemToken.getHeader("Authorization")).thenReturn(null);
        assertThat(provider.resolveToken(requestSemToken)).isNull();

        HttpServletRequest requestSemBearer = mock(HttpServletRequest.class);
        when(requestSemBearer.getHeader("Authorization")).thenReturn("Basic abc123");
        assertThat(provider.resolveToken(requestSemBearer)).isNull();
    }

    @Test
    @DisplayName("construtor - secret nulo causa excecao")
    void construtorSecretNulo() {
        JwtProperties props = new JwtProperties();
        props.setSecret(null);
        props.setExpirationMs(3600000L);
        props.setRefreshExpirationMs(86400000L);

        org.junit.jupiter.api.Assertions.assertThrows(
                NullPointerException.class,
                () -> new JwtTokenProvider(props));
    }
}
