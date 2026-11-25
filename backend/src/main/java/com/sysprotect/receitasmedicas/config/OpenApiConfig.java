package com.sysprotect.receitasmedicas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI blogOpenApiConfig() {
        return new OpenAPI().info(new Info()
                        .title("SysProtect API - API Corporativa")
                        .version("1.0.0")
                        .description("API para detecção de fraudes"));
    }
}
