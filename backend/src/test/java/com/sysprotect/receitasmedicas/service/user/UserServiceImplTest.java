package com.sysprotect.receitasmedicas.service.user;

import com.sysprotect.receitasmedicas.dto.request.RegisterRequest;
import com.sysprotect.receitasmedicas.dto.response.UserResponse;
import com.sysprotect.receitasmedicas.exception.UserAlreadyExistsException;
import com.sysprotect.receitasmedicas.exception.UserNotFoundException;
import com.sysprotect.receitasmedicas.model.AppUser;
import com.sysprotect.receitasmedicas.model.RoleModel;
import com.sysprotect.receitasmedicas.repository.AppUserRepository;
import com.sysprotect.receitasmedicas.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private AppUser testUser;
    private RoleModel userRole;
    private RegisterRequest registerRequest;

    @BeforeEach
    void setUp() {
        UUID userId = UUID.randomUUID();
        userRole = new RoleModel("USER", true);

        testUser = new AppUser("testuser", "encodedPassword", "Test User");
        testUser.setId(userId);
        Set<RoleModel> roles = new HashSet<>();
        roles.add(userRole);
        testUser.setRoles(roles);

        registerRequest = new RegisterRequest();
        registerRequest.setUsername("newuser");
        registerRequest.setPassword("password123");
        registerRequest.setEmail("newuser@test.com");
        registerRequest.setFullName("New User");
    }

    @Test
    @DisplayName("Should return all users")
    void shouldReturnAllUsers() {
        // Given
        when(appUserRepository.findAll()).thenReturn(List.of(testUser));

        // When
        List<UserResponse> users = userService.findAll();

        // Then
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo("testuser");
        verify(appUserRepository).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no users exist")
    void shouldReturnEmptyListWhenNoUsers() {
        // Given
        when(appUserRepository.findAll()).thenReturn(List.of());

        // When
        List<UserResponse> users = userService.findAll();

        // Then
        assertThat(users).isEmpty();
    }

    @Test
    @DisplayName("Should find user by ID when exists")
    void shouldFindUserById() {
        // Given
        UUID userId = testUser.getId();
        when(appUserRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // When
        Optional<UserResponse> found = userService.findById(userId);

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("testuser");
        verify(appUserRepository).findById(userId);
    }

    @Test
    @DisplayName("Should return empty when user not found by ID")
    void shouldReturnEmptyWhenUserNotFoundById() {
        // Given
        UUID userId = UUID.randomUUID();
        when(appUserRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        Optional<UserResponse> found = userService.findById(userId);

        // Then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should find user by username when exists")
    void shouldFindUserByUsername() {
        // Given
        when(appUserRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // When
        Optional<UserResponse> found = userService.findByUsername("testuser");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("testuser");
        verify(appUserRepository).findByUsername("testuser");
    }

    @Test
    @DisplayName("Should return empty when user not found by username")
    void shouldReturnEmptyWhenUserNotFoundByUsername() {
        // Given
        when(appUserRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // When
        Optional<UserResponse> found = userService.findByUsername("nonexistent");

        // Then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should create user successfully")
    void shouldCreateUserSuccessfully() {
        // Given
        when(appUserRepository.existsByUsername("newuser")).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        AppUser savedUser = new AppUser("newuser", "encodedPassword", "New User");
        savedUser.setId(UUID.randomUUID());
        Set<RoleModel> roles = new HashSet<>();
        roles.add(userRole);
        savedUser.setRoles(roles);
        when(appUserRepository.save(any(AppUser.class))).thenReturn(savedUser);

        // When
        UserResponse response = userService.create(registerRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo("newuser");
        assertThat(response.getFullName()).isEqualTo("New User");
        assertThat(response.getRoles()).contains("USER");
        verify(appUserRepository).existsByUsername("newuser");
        verify(passwordEncoder).encode("password123");
        verify(appUserRepository).save(any(AppUser.class));
    }

    @Test
    @DisplayName("Should throw UserAlreadyExistsException when username exists")
    void shouldThrowExceptionWhenUsernameExists() {
        // Given
        when(appUserRepository.existsByUsername("newuser")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> userService.create(registerRequest))
            .isInstanceOf(UserAlreadyExistsException.class)
            .hasMessage("Username já existe: newuser");
        verify(appUserRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when role USER not found")
    void shouldThrowExceptionWhenRoleUserNotFound() {
        // Given
        when(appUserRepository.existsByUsername("newuser")).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.create(registerRequest))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("Role USER não encontrada");
    }

    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUserSuccessfully() {
        // Given
        UUID userId = testUser.getId();
        RegisterRequest updateRequest = new RegisterRequest();
        updateRequest.setUsername("updateduser");
        updateRequest.setPassword("newpassword");
        updateRequest.setFullName("Updated User");

        when(appUserRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.encode("newpassword")).thenReturn("newEncodedPassword");
        when(appUserRepository.save(any(AppUser.class))).thenAnswer(inv -> inv.getArgument(0));

        // When
        UserResponse response = userService.update(userId, updateRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo("updateduser");
        assertThat(response.getFullName()).isEqualTo("Updated User");
        verify(appUserRepository).findById(userId);
        verify(passwordEncoder).encode("newpassword");
        verify(appUserRepository).save(testUser);
    }

    @Test
    @DisplayName("Should update user without changing password when blank")
    void shouldUpdateUserWithoutChangingPasswordWhenBlank() {
        // Given
        UUID userId = testUser.getId();
        RegisterRequest updateRequest = new RegisterRequest();
        updateRequest.setUsername("updateduser");
        updateRequest.setPassword("");
        updateRequest.setFullName("Updated User");

        when(appUserRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(appUserRepository.save(any(AppUser.class))).thenAnswer(inv -> inv.getArgument(0));

        // When
        userService.update(userId, updateRequest);

        // Then
        verify(passwordEncoder, never()).encode(anyString());
        verify(appUserRepository).save(testUser);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when updating non-existent user")
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        // Given
        UUID userId = UUID.randomUUID();
        when(appUserRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userService.update(userId, registerRequest))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("Usuário não encontrado com ID: " + userId);
    }

    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUserSuccessfully() {
        // Given
        UUID userId = testUser.getId();
        when(appUserRepository.existsById(userId)).thenReturn(true);

        // When
        userService.delete(userId);

        // Then
        verify(appUserRepository).existsById(userId);
        verify(appUserRepository).deleteById(userId);
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when deleting non-existent user")
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        // Given
        UUID userId = UUID.randomUUID();
        when(appUserRepository.existsById(userId)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> userService.delete(userId))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("Usuário não encontrado com ID: " + userId);
        verify(appUserRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should return true when username exists")
    void shouldReturnTrueWhenUsernameExists() {
        // Given
        when(appUserRepository.existsByUsername("testuser")).thenReturn(true);

        // When
        boolean exists = userService.existsByUsername("testuser");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when username does not exist")
    void shouldReturnFalseWhenUsernameDoesNotExist() {
        // Given
        when(appUserRepository.existsByUsername("nonexistent")).thenReturn(false);

        // When
        boolean exists = userService.existsByUsername("nonexistent");

        // Then
        assertThat(exists).isFalse();
    }
}
