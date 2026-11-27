
package com.sysprotect.receitasmedicas.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sysprotect.receitasmedicas.model.TipoFraude;

public interface TipoFraudeRepository
        extends JpaRepository<TipoFraude, UUID>,
                JpaSpecificationExecutor<TipoFraude> {

}
