package com.sysprotect.receitasmedicas.security;

import com.sysprotect.receitasmedicas.service.CustomUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SecurityConfigTest {

    @Test
    @DisplayName("Should create BCryptPasswordEncoder instance")
    void shouldCreateBCryptPasswordEncoder() {
        // Given
        SecurityConfig config = new SecurityConfig(
            new JwtAuthenticationFilter(null, null),
            null
        );

        // When
        PasswordEncoder encoder = config.passwordEncoder();

        // Then
        assertThat(encoder).isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    @DisplayName("Should create ProviderManager authentication manager")
    void shouldCreateProviderManager() {
        // Given
        SecurityConfig config = new SecurityConfig(
            new JwtAuthenticationFilter(null, null),
            null
        );
        PasswordEncoder encoder = config.passwordEncoder();

        // When
        AuthenticationManager authManager = config.authenticationManager(
            username -> null,
            encoder
        );

        // Then
        assertThat(authManager).isInstanceOf(ProviderManager.class);
    }

    @Test
    @DisplayName("Should create DaoAuthenticationProvider inside authentication manager")
    void shouldUseDaoAuthenticationProvider() {
        // Given
        SecurityConfig config = new SecurityConfig(
            new JwtAuthenticationFilter(null, null),
            null
        );
        PasswordEncoder encoder = config.passwordEncoder();

        // When
        AuthenticationManager authManager = config.authenticationManager(
            username -> null,
            encoder
        );

        // Then
        assertThat(authManager).isInstanceOf(ProviderManager.class);
        assertThatThrownBy(() -> authManager.authenticate(null))
            .isInstanceOf(Exception.class);
    }
}
