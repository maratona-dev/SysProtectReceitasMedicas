package com.sysprotect.receitasmedicas.service.auth;

import com.sysprotect.receitasmedicas.dto.request.LoginRequest;
import com.sysprotect.receitasmedicas.dto.response.AuthResponse;
import com.sysprotect.receitasmedicas.exception.InvalidCredentialsException;
import com.sysprotect.receitasmedicas.model.AppUser;
import com.sysprotect.receitasmedicas.model.RoleModel;
import com.sysprotect.receitasmedicas.repository.AppUserRepository;
import com.sysprotect.receitasmedicas.repository.RoleRepository;
import com.sysprotect.receitasmedicas.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    private AppUser testUser;
    private RoleModel userRole;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        UUID userId = UUID.randomUUID();
        userRole = new RoleModel("USER", true);

        testUser = new AppUser("testuser", "encodedPassword", "Test User");
        testUser.setId(userId);
        Set<RoleModel> roles = new HashSet<>();
        roles.add(userRole);
        testUser.setRoles(roles);

        loginRequest = new LoginRequest("testuser", "password123");
    }

    @Test
    @DisplayName("Should authenticate successfully and return token with user data")
    void shouldLoginSuccessfully() {
        // Given
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        when(jwtTokenProvider.createToken(eq("testuser"), anyString())).thenReturn("jwt-token-123");
        when(appUserRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // When
        AuthResponse response = authService.login(loginRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("jwt-token-123");
        assertThat(response.getUsername()).isEqualTo("testuser");
        assertThat(response.getId()).isEqualTo(testUser.getId());
        assertThat(response.getRoles()).contains("USER");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).createToken("testuser", "USER");
        verify(appUserRepository).findByUsername("testuser");
    }

    @Test
    @DisplayName("Should throw InvalidCredentialsException when authentication fails")
    void shouldThrowExceptionWhenAuthenticationFails() {
        // Given
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new RuntimeException("Invalid credentials"));

        // When & Then
        assertThatThrownBy(() -> authService.login(loginRequest))
            .isInstanceOf(InvalidCredentialsException.class)
            .hasMessage("Credenciais inválidas");
    }

    @Test
    @DisplayName("Should throw InvalidCredentialsException when user not found after auth")
    void shouldThrowExceptionWhenUserNotFoundAfterAuth() {
        // Given
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        when(appUserRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> authService.login(loginRequest))
            .isInstanceOf(InvalidCredentialsException.class)
            .hasMessage("Credenciais inválidas");
    }

    @Test
    @DisplayName("Should refresh token successfully")
    void shouldRefreshTokenSuccessfully() {
        // Given
        String token = "valid-jwt-token";
        when(jwtTokenProvider.validateToken(token)).thenReturn(true);
        when(jwtTokenProvider.getUsername(token)).thenReturn("testuser");
        when(jwtTokenProvider.createToken(eq("testuser"), anyString())).thenReturn("new-jwt-token");
        when(appUserRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // When
        AuthResponse response = authService.refresh(token);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("new-jwt-token");
        assertThat(response.getUsername()).isEqualTo("testuser");
        verify(jwtTokenProvider).validateToken(token);
        verify(jwtTokenProvider).getUsername(token);
        verify(appUserRepository).findByUsername("testuser");
    }

    @Test
    @DisplayName("Should throw InvalidCredentialsException when refreshing invalid token")
    void shouldThrowExceptionWhenRefreshingInvalidToken() {
        // Given
        String token = "invalid-token";
        when(jwtTokenProvider.validateToken(token)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> authService.refresh(token))
            .isInstanceOf(InvalidCredentialsException.class)
            .hasMessage("Token inválido ou expirado");
    }

    @Test
    @DisplayName("Should logout without error")
    void shouldLogoutWithoutError() {
        // Given
        String token = "some-token";

        // When - should not throw
        authService.logout(token);

        // Then - just verifies no exception
        verifyNoInteractions(jwtTokenProvider, appUserRepository);
    }

    @Test
    @DisplayName("Should return roles list containing USER for test user")
    void shouldReturnRolesContainingUser() {
        // Given
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testuser");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        when(appUserRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(jwtTokenProvider.createToken(eq("testuser"), anyString())).thenReturn("token");

        // When
        AuthResponse response = authService.login(loginRequest);

        // Then
        assertThat(response.getRoles()).containsExactly("USER");
    }
}
