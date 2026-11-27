package com.sysprotect.receitasmedicas.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtPropertiesTest {

    @Test
    @DisplayName("JwtProperties - getters e setters")
    void gettersSettersTest() {
        JwtProperties props = new JwtProperties();
        props.setSecret("minha-chave-secreta");
        props.setExpirationMs(3600000L);
        props.setRefreshExpirationMs(86400000L);

        assertThat(props.getSecret()).isEqualTo("minha-chave-secreta");
        assertThat(props.getExpirationMs()).isEqualTo(3600000L);
        assertThat(props.getRefreshExpirationMs()).isEqualTo(86400000L);
    }
}
