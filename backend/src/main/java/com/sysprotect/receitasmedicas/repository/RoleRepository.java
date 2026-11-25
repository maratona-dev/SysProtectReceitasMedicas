package com.sysprotect.receitasmedicas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sysprotect.receitasmedicas.model.RoleModel;



public interface RoleRepository extends JpaRepository<RoleModel, Long> {

    Optional<RoleModel> findByName(String name);
}
