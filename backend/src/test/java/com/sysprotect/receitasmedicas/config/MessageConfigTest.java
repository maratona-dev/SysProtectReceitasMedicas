package com.sysprotect.receitasmedicas.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Locale;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

class MessageConfigTest {

    @Test
    @DisplayName("MessageConfig - messageSource configurado")
    void messageSourceTest() {
        MessageConfig config = new MessageConfig();
        ReloadableResourceBundleMessageSource source =
                (ReloadableResourceBundleMessageSource) config.messageSource();

        assertThat(source).isNotNull();
        assertThat(source.getBasenameSet()).contains("classpath:messages");
        assertThatThrownBy(() -> source.getMessage("nao.existe", null, Locale.ROOT))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    @DisplayName("MessageConfig - localeResolver configurado")
    void localeResolverTest() {
        MessageConfig config = new MessageConfig();
        AcceptHeaderLocaleResolver resolver =
                (AcceptHeaderLocaleResolver) config.localeResolver();

        assertThat(resolver).isNotNull();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Accept-Language")).thenReturn("pt-BR");
        when(request.getLocales()).thenReturn(java.util.Collections.emptyEnumeration());
        assertThat(resolver.resolveLocale(request)).isEqualTo(Locale.of("pt", "BR"));
        assertThat(resolver.getSupportedLocales())
                .containsExactlyInAnyOrder(
                        Locale.of("pt", "BR"),
                        Locale.of("en", "US"),
                        Locale.of("es", "ES"),
                        Locale.of("fr", "FR"),
                        Locale.of("de", "DE"));
    }
}
