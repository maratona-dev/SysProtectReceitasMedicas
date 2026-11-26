package com.sysprotect.receitasmedicas.service.auth;

import com.sysprotect.receitasmedicas.dto.request.LoginRequest;
import com.sysprotect.receitasmedicas.dto.response.AuthResponse;

import java.util.UUID;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse refresh(String token);

    void logout(String token);
}
