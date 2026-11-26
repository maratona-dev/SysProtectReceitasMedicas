package com.sysprotect.receitasmedicas.security;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    private AppUser testUser;
    private RoleModel userRole;

    @BeforeEach
    void setUp() {
        UUID userId = UUID.randomUUID();
        userRole = new RoleModel("USER", true);

        testUser = new AppUser("testuser", "password", "Test User");
        testUser.setId(userId);
        testUser.setEnabled(true);
        Set<RoleModel> roles = new HashSet<>();
        roles.add(userRole);
        testUser.setRoles(roles);

        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should pass through when no Authorization header")
    void shouldPassThroughWhenNoAuthorizationHeader() throws Exception {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        // When
        filter.doFilterInternal(request, response, chain);

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(jwtTokenProvider, never()).validateToken(any());
    }

    @Test
    @DisplayName("Should pass through when header does not start with Bearer")
    void shouldPassThroughWhenHeaderDoesNotStartWithBearer() throws Exception {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic somecredentials");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        // When
        filter.doFilterInternal(request, response, chain);

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(jwtTokenProvider, never()).validateToken(any());
    }

    @Test
    @DisplayName("Should pass through when token is invalid")
    void shouldPassThroughWhenTokenIsInvalid() throws Exception {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        when(jwtTokenProvider.validateToken("invalid-token")).thenReturn(false);

        // When
        filter.doFilterInternal(request, response, chain);

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("Should authenticate user when valid token provided")
    void shouldAuthenticateUserWhenValidTokenProvided() throws Exception {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        when(jwtTokenProvider.validateToken("valid-token")).thenReturn(true);
        when(jwtTokenProvider.getUsername("valid-token")).thenReturn("testuser");
        when(appUserRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // When
        filter.doFilterInternal(request, response, chain);

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("testuser");
        assertThat(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()).isTrue();
    }

    @Test
    @DisplayName("Should not authenticate when user not found in database")
    void shouldNotAuthenticateWhenUserNotFound() throws Exception {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        when(jwtTokenProvider.validateToken("valid-token")).thenReturn(true);
        when(jwtTokenProvider.getUsername("valid-token")).thenReturn("nonexistent");
        when(appUserRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        // When
        filter.doFilterInternal(request, response, chain);

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    @DisplayName("Should not re-authenticate when already authenticated")
    void shouldNotReAuthenticateWhenAlreadyAuthenticated() throws Exception {
        // Given
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken("existing", "password")
        );

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        when(jwtTokenProvider.validateToken("valid-token")).thenReturn(true);

        // When
        filter.doFilterInternal(request, response, chain);

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("existing");
        verify(jwtTokenProvider, never()).getUsername(any());
    }

    @Test
    @DisplayName("Should set authentication details from request")
    void shouldSetAuthenticationDetailsFromRequest() throws Exception {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer valid-token");
        request.setRemoteAddr("192.168.1.1");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        when(jwtTokenProvider.validateToken("valid-token")).thenReturn(true);
        when(jwtTokenProvider.getUsername("valid-token")).thenReturn("testuser");
        when(appUserRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        // When
        filter.doFilterInternal(request, response, chain);

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getDetails())
            .isInstanceOf(WebAuthenticationDetailsSource.class);
    }
}
