package com.sysprotect.receitasmedicas.controller;

import com.sysprotect.receitasmedicas.dto.request.LoginRequest;
import com.sysprotect.receitasmedicas.dto.request.RegisterRequest;
import com.sysprotect.receitasmedicas.dto.response.AuthResponse;
import com.sysprotect.receitasmedicas.service.auth.AuthService;
import com.sysprotect.receitasmedicas.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticação", description = "Endpoints para login, cadastro e gerenciamento de tokens JWT")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Operation(summary = "Login do usuário", description = "Autentica usuário e retorna token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cadastro de novo usuário", description = "Cria um novo usuário no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Usuário já existe")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        // Cadastra o usuário no banco primeiro, depois autentica para retornar o token
        userService.create(registerRequest);
        AuthResponse response = authService.login(new LoginRequest(
            registerRequest.getUsername(),
            registerRequest.getPassword()
        ));
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Renovar token JWT", description = "Gera um novo token JWT baseado no token atual")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token renovado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Token inválido ou expirado")
    })
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestHeader(value = "Authorization", required = false) String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        String token = authorization.substring(7);
        AuthResponse response = authService.refresh(token);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Logout do usuário", description = "Invalida a sessão (cliente deve remover o token)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logout realizado")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            authService.logout(token);
        }
        return ResponseEntity.ok().build();
    }
}
