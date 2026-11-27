package com.sysprotect.receitasmedicas.service.user;

import com.sysprotect.receitasmedicas.dto.request.RegisterRequest;
import com.sysprotect.receitasmedicas.dto.response.UserResponse;
import com.sysprotect.receitasmedicas.model.AppUser;
import com.sysprotect.receitasmedicas.model.RoleModel;
import com.sysprotect.receitasmedicas.exception.UserAlreadyExistsException;
import com.sysprotect.receitasmedicas.exception.UserNotFoundException;
import com.sysprotect.receitasmedicas.repository.AppUserRepository;
import com.sysprotect.receitasmedicas.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(AppUserRepository appUserRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return appUserRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> findById(UUID id) {
        return appUserRepository.findById(id)
            .map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> findByUsername(String username) {
        return appUserRepository.findByUsername(username)
            .map(this::toResponse);
    }

    @Override
    public UserResponse create(RegisterRequest request) {
        if (appUserRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username já existe: " + request.getUsername());
        }

        RoleModel userRole = roleRepository.findByName("ROLE_USER")
            .orElseThrow(() -> new UserNotFoundException("Role ROLE_USER não encontrada"));

        Set<RoleModel> roles = new HashSet<>();
        roles.add(userRole);

        AppUser user = new AppUser(
            request.getUsername(),
            passwordEncoder.encode(request.getPassword()),
            request.getFullName()
        );
        user.setRoles(roles);

        AppUser saved = appUserRepository.save(user);
        return toResponse(saved);
    }

    @Override
    public UserResponse update(UUID id, RegisterRequest request) {
        AppUser existing = appUserRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id));

        existing.setUsername(request.getUsername());
        existing.setFullName(request.getFullName());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        AppUser updated = appUserRepository.save(existing);
        return toResponse(updated);
    }

    @Override
    public void delete(UUID id) {
        if (!appUserRepository.existsById(id)) {
            throw new UserNotFoundException("Usuário não encontrado com ID: " + id);
        }
        appUserRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return appUserRepository.existsByUsername(username);
    }

    private UserResponse toResponse(AppUser user) {
        List<String> roles = user.getRoles().stream()
            .filter(RoleModel::isEnabled)
            .map(RoleModel::getName)
            .collect(Collectors.toList());

        return new UserResponse(
            null, // id - AppUser usa UUID, UserResponse usa Long
            user.getUsername(),
            null, // email - não existe em AppUser
            user.getFullName(),
            user.isEnabled(),
            roles,
            null, // createdAt - não existe em AppUser
            null  // lastLoginAt - não existe em AppUser
        );
    }
}
