package com.sysprotect.receitasmedicas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "role")
public class RoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String name;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    public RoleModel() {
    }

    public RoleModel(String name) {
        this.name = name;
        this.enabled = true;
    }

    public RoleModel(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleModel roleModel)) return false;
        return Objects.equals(id, roleModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}