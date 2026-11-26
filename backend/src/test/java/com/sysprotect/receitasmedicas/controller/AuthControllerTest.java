package com.sysprotect.receitasmedicas.controller;

import com.sysprotect.receitasmedicas.dto.request.LoginRequest;
import com.sysprotect.receitasmedicas.dto.request.RegisterRequest;
import com.sysprotect.receitasmedicas.dto.response.AuthResponse;
import com.sysprotect.receitasmedicas.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Should return 200 and auth response on successful login")
    void shouldReturn200OnSuccessfulLogin() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest("user", "pass");
        AuthResponse response = new AuthResponse(
            "token-123",
            UUID.randomUUID(),
            "user",
            "user@test.com",
            List.of("USER")
        );
        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("token-123"))
            .andExpect(jsonPath("$.type").value("Bearer"))
            .andExpect(jsonPath("$.username").value("user"))
            .andExpect(jsonPath("$.roles[0]").value("USER"));

        verify(authService).login(any(LoginRequest.class));
    }

    @Test
    @DisplayName("Should return 201 on successful registration")
    void shouldReturn201OnSuccessfulRegistration() throws Exception {
        // Given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setPassword("password123");
        registerRequest.setFullName("New User");

        AuthResponse response = new AuthResponse(
            "token-456",
            UUID.randomUUID(),
            "newuser",
            null,
            List.of("USER")
        );
        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.token").value("token-456"))
            .andExpect(jsonPath("$.username").value("newuser"));
    }

    @Test
    @DisplayName("Should return 200 on token refresh")
    void shouldReturn200OnTokenRefresh() throws Exception {
        // Given
        String token = "valid-token";
        AuthResponse response = new AuthResponse(
            "new-token",
            UUID.randomUUID(),
            "user",
            null,
            List.of("USER")
        );
        when(authService.refresh(eq(token))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/auth/refresh")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("new-token"));

        verify(authService).refresh(eq(token));
    }

    @Test
    @DisplayName("Should return 401 on refresh without authorization header")
    void shouldReturn401OnRefreshWithoutAuthHeader() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/v1/auth/refresh"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should return 401 on refresh with invalid authorization header")
    void shouldReturn401OnRefreshWithInvalidAuthHeader() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/v1/auth/refresh")
                .header("Authorization", "InvalidFormat"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should return 200 on logout")
    void shouldReturn200OnLogout() throws Exception {
        // Given
        String token = "some-token";

        // When & Then
        mockMvc.perform(post("/api/v1/auth/logout")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());

        verify(authService).logout(eq(token));
    }

    @Test
    @DisplayName("Should return 200 on logout without authorization header")
    void shouldReturn200OnLogoutWithoutAuthHeader() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/v1/auth/logout"))
            .andExpect(status().isOk());

        verifyNoInteractions(authService);
    }

    @Test
    @DisplayName("Should return 400 on login with blank username")
    void shouldReturn400OnLoginWithBlankUsername() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest("", "pass");

        // When & Then
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 on login with blank password")
    void shouldReturn400OnLoginWithBlankPassword() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest("user", "");

        // When & Then
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
            .andExpect(status().isBadRequest());
    }
}
