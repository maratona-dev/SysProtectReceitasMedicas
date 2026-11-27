package com.sysprotect.receitasmedicas.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OpenApiConfigTest {

    @Test
    @DisplayName("OpenApiConfig - bean OpenAPI criado")
    void openApiConfigTest() {
        OpenApiConfig config = new OpenApiConfig();
        var openAPI = config.blogOpenApiConfig();

        assertThat(openAPI).isNotNull();
        assertThat(openAPI.getInfo().getTitle()).isEqualTo("SysProtect API - API Corporativa");
        assertThat(openAPI.getInfo().getVersion()).isEqualTo("1.0.0");
        assertThat(openAPI.getInfo().getDescription()).isEqualTo("API para detecção de fraudes");
    }
}
