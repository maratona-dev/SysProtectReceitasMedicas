package com.sysprotect.receitasmedicas.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserResponse {

    private UUID id;
    private String username;
    private String email;
    private String fullName;
    private Boolean enabled;
    private List<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    public UserResponse() {
    }

    public UserResponse(UUID id, String username, String email, String fullName,
                        Boolean enabled, List<String> roles, LocalDateTime createdAt, LocalDateTime lastLoginAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.enabled = enabled;
        this.roles = roles;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
}
