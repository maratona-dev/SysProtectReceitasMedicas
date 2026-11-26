package com.sysprotect.receitasmedicas.repository;

import com.sysprotect.receitasmedicas.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

    Optional<RoleModel> findByRoleName(String roleName);

    boolean existsByRoleName(String roleName);
}
