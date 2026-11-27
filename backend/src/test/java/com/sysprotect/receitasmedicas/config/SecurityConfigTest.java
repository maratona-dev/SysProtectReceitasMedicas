package com.sysprotect.receitasmedicas.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sysprotect.receitasmedicas.service.CustomUserDetailsService;
import com.sysprotect.receitasmedicas.security.JwtAuthenticationFilter;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Test
    @DisplayName("SecurityConfig - passwordEncoder retorna BCryptPasswordEncoder")
    void passwordEncoderTest() throws Exception {
        SecurityConfig config = new SecurityConfig(jwtAuthenticationFilter, userDetailsService);
        PasswordEncoder encoder = config.passwordEncoder();

        assertThat(encoder).isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    @DisplayName("SecurityConfig - authenticationProvider retorna DaoAuthenticationProvider")
    void authenticationProviderTest() throws Exception {
        SecurityConfig config = new SecurityConfig(jwtAuthenticationFilter, userDetailsService);
        DaoAuthenticationProvider provider = config.authenticationProvider();

        assertThat(provider).isNotNull();
        assertThat(provider.getUserDetailsService()).isEqualTo(userDetailsService);
        assertThat(provider.getPasswordEncoder()).isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    @DisplayName("SecurityConfig - authenticationManager delega para configuration")
    void authenticationManagerTest() throws Exception {
        AuthenticationConfiguration configuration = mock(AuthenticationConfiguration.class);
        AuthenticationManager manager = mock(AuthenticationManager.class);
        when(configuration.getAuthenticationManager()).thenReturn(manager);

        SecurityConfig config = new SecurityConfig(jwtAuthenticationFilter, userDetailsService);
        AuthenticationManager result = config.authenticationManager(configuration);

        assertThat(result).isEqualTo(manager);
    }

    @Test
    @DisplayName("SecurityConfig - corsConfigurationSource configurado")
    void corsConfigurationSourceTest() throws Exception {
        SecurityConfig config = new SecurityConfig(jwtAuthenticationFilter, userDetailsService);
        var source = config.corsConfigurationSource();

        assertThat(source).isNotNull();
    }
}
