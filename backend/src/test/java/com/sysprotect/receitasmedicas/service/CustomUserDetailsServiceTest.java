package com.sysprotect.receitasmedicas.service;

import com.sysprotect.receitasmedicas.model.AppUser;
import com.sysprotect.receitasmedicas.model.RoleModel;
import com.sysprotect.receitasmedicas.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private AppUser testUser;
    private RoleModel userRole;

    @BeforeEach
    void setUp() {
        UUID userId = UUID.randomUUID();
        userRole = new RoleModel("USER", true);

        testUser = new AppUser("testuser", "$2a$10$encodedPassword", "Test User");
        testUser.setId(userId);
        testUser.setEnabled(true);
        Set<RoleModel> roles = new HashSet<>();
        roles.add(userRole);
        testUser.setRoles(roles);
    }

    @Test
    @DisplayName("Should load user by username when exists")
    void shouldLoadUserByUsernameWhenExists() {
        // Given
        when(appUserRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Then
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("testuser");
        assertThat(userDetails.getPassword()).isEqualTo("$2a$10$encodedPassword");
        assertThat(userDetails.isEnabled()).isTrue();
        assertThat(userDetails.getAuthorities()).hasSize(1);
        assertThat(userDetails.getAuthorities().iterator().next().getAuthority()).isEqualTo("USER");
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user not found")
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        when(appUserRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("nonexistent"))
            .isInstanceOf(UsernameNotFoundException.class)
            .hasMessage("Usuário não encontrado: nonexistent");
    }

    @Test
    @DisplayName("Should filter disabled roles when loading user")
    void shouldFilterDisabledRolesWhenLoadingUser() {
        // Given
        RoleModel disabledRole = new RoleModel("DISABLED_ROLE", false);
        Set<RoleModel> roles = new HashSet<>();
        roles.add(userRole);
        roles.add(disabledRole);
        testUser.setRoles(roles);

        when(appUserRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Then
        assertThat(userDetails.getAuthorities()).hasSize(1);
        assertThat(userDetails.getAuthorities().iterator().next().getAuthority()).isEqualTo("USER");
    }

    @Test
    @DisplayName("Should return disabled user when enabled is false")
    void shouldReturnDisabledUserWhenEnabledFalse() {
        // Given
        testUser.setEnabled(false);
        when(appUserRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Then
        assertThat(userDetails.isEnabled()).isFalse();
    }
}
