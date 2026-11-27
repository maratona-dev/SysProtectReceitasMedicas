package com.sysprotect.receitasmedicas;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReceitasmedicasApplicationTest {

    @Test
    @DisplayName("ReceitasmedicasApplication - classe existe e e SpringBootApplication")
    void applicationTest() {
        assertThat(ReceitasmedicasApplication.class).isNotNull();
        assertThat(ReceitasmedicasApplication.class
                .isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class))
                .isTrue();
    }
}
