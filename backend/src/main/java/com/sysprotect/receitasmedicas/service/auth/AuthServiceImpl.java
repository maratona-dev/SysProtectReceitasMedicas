package com.sysprotect.receitasmedicas.service.auth;

import com.sysprotect.receitasmedicas.dto.request.LoginRequest;
import com.sysprotect.receitasmedicas.dto.response.AuthResponse;
import com.sysprotect.receitasmedicas.model.AppUser;
import com.sysprotect.receitasmedicas.model.RoleModel;
import com.sysprotect.receitasmedicas.exception.InvalidCredentialsException;
import com.sysprotect.receitasmedicas.repository.AppUserRepository;
import com.sysprotect.receitasmedicas.repository.RoleRepository;
import com.sysprotect.receitasmedicas.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider,
                           AppUserRepository appUserRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            String username = authentication.getName();
            String refreshToken = jwtTokenProvider.createRefreshToken(username);

            AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Usuário não encontrado"));

            String primaryRole = user.getRoles().stream()
                .filter(RoleModel::isEnabled)
                .findFirst()
                .map(RoleModel::getName)
                .orElse("USER");

            List<String> roles = user.getRoles().stream()
                .filter(RoleModel::isEnabled)
                .map(RoleModel::getName)
                .toList();

            String token = jwtTokenProvider.createToken(username, primaryRole);

            return new AuthResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                roles
            );
        } catch (Exception e) {
            throw new InvalidCredentialsException("Credenciais inválidas");
        }
    }

    @Override
    public AuthResponse refresh(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidCredentialsException("Token inválido ou expirado");
        }

        String username = jwtTokenProvider.getUsername(token);
        AppUser user = appUserRepository.findByUsername(username)
            .orElseThrow(() -> new InvalidCredentialsException("Usuário não encontrado"));

        String primaryRole = user.getRoles().stream()
            .filter(RoleModel::isEnabled)
            .findFirst()
            .map(RoleModel::getName)
            .orElse("USER");

        String newToken = jwtTokenProvider.createToken(username, primaryRole);

        List<String> roles = user.getRoles().stream()
            .filter(RoleModel::isEnabled)
            .map(RoleModel::getName)
            .toList();

        return new AuthResponse(
            newToken,
            user.getId(),
            user.getUsername(),
            user.getFullName(),
            roles
        );
    }

    @Override
    public void logout(String token) {
        // Stateless JWT - logout handled client-side by removing token
    }
}
