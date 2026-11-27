package com.sysprotect.receitasmedicas.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MessageConfigTest {

    @Test
    @DisplayName("MessageConfig - messageSource configurado")
    void messageSourceTest() {
        MessageConfig config = new MessageConfig();
        var source = config.messageSource();

        assertThat(source).isNotNull();
        assertThat(source.getBasenames()).contains("classpath:messages");
        assertThat(source.getDefaultEncoding()).isEqualTo(StandardCharsets.UTF_8.name());
        assertThat(source.isFallbackToSystemLocale()).isFalse();
    }

    @Test
    @DisplayName("MessageConfig - localeResolver configurado")
    void localeResolverTest() {
        MessageConfig config = new MessageConfig();
        var resolver = config.localeResolver();

        assertThat(resolver).isNotNull();
        assertThat(resolver.resolveLocale(null)).isEqualTo(Locale.of("pt", "BR"));
        assertThat(resolver.getSupportedLocales())
                .containsExactlyInAnyOrder(
                        Locale.of("pt", "BR"),
                        Locale.of("en", "US"),
                        Locale.of("es", "ES"),
                        Locale.of("fr", "FR"),
                        Locale.of("de", "DE"));
    }
}
