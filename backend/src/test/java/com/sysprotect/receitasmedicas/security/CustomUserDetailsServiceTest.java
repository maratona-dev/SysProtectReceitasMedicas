package com.sysprotect.receitasmedicas.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sysprotect.receitasmedicas.model.AppUser;
import com.sysprotect.receitasmedicas.model.RoleModel;
import com.sysprotect.receitasmedicas.repository.AppUserRepository;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Test
    @DisplayName("loadUserByUsername - usuario encontrado retorna UserDetails")
    void loadUserByUsernameSucesso() {
        AppUserRepository repository = mock(AppUserRepository.class);

        RoleModel role = new RoleModel("ADMIN");
        role.setEnabled(true);

        AppUser user = new AppUser("usuario", "senha", "Usuario Teste");
        user.setRoles(Set.of(role));
        user.setEnabled(true);

        when(repository.findByUsername("usuario")).thenReturn(java.util.Optional.of(user));

        CustomUserDetailsService service = new CustomUserDetailsService(repository);
        UserDetails userDetails = service.loadUserByUsername("usuario");

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("usuario");
        assertThat(userDetails.getPassword()).isEqualTo("senha");
        assertThat(userDetails.isEnabled()).isTrue();
        assertThat(userDetails.getAuthorities()).hasSize(1);
        assertThat(userDetails.getAuthorities().iterator().next().getAuthority()).isEqualTo("ADMIN");
    }

    @Test
    @DisplayName("loadUserByUsername - usuario nao encontrado lança excecao")
    void loadUserByUsernameNaoEncontrado() {
        AppUserRepository repository = mock(AppUserRepository.class);
        when(repository.findByUsername("naoexiste")).thenReturn(java.util.Optional.empty());

        CustomUserDetailsService service = new CustomUserDetailsService(repository);

        org.junit.jupiter.api.Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername("naoexiste"));
    }

    @Test
    @DisplayName("loadUserByUsername - usuario desabilitado")
    void loadUserByUsuarioDesabilitado() {
        AppUserRepository repository = mock(AppUserRepository.class);

        AppUser user = new AppUser("usuario", "senha", "Usuario Teste");
        user.setEnabled(false);

        when(repository.findByUsername("usuario")).thenReturn(java.util.Optional.of(user));

        CustomUserDetailsService service = new CustomUserDetailsService(repository);
        UserDetails userDetails = service.loadUserByUsername("usuario");

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.isEnabled()).isFalse();
    }

    @Test
    @DisplayName("loadUserByUsername - role desabilitada nao vira authority")
    void loadUserByUsernameRoleDesabilitada() {
        AppUserRepository repository = mock(AppUserRepository.class);

        RoleModel roleDesabilitada = new RoleModel("ADMIN", false);
        AppUser user = new AppUser("usuario", "senha", "Usuario Teste");
        user.setRoles(Set.of(roleDesabilitada));
        user.setEnabled(true);

        when(repository.findByUsername("usuario")).thenReturn(java.util.Optional.of(user));

        CustomUserDetailsService service = new CustomUserDetailsService(repository);
        UserDetails userDetails = service.loadUserByUsername("usuario");

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getAuthorities()).isEmpty();
    }
}
