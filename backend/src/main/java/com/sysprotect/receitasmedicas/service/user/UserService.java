package com.sysprotect.receitasmedicas.service.user;

import com.sysprotect.receitasmedicas.dto.request.RegisterRequest;
import com.sysprotect.receitasmedicas.dto.response.UserResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserResponse> findAll();

    Optional<UserResponse> findById(UUID id);

    Optional<UserResponse> findByUsername(String username);

    UserResponse create(RegisterRequest request);

    UserResponse update(UUID id, RegisterRequest request);

    void delete(UUID id);

    boolean existsByUsername(String username);
}
