package com.sysprotect.receitasmedicas.service;


import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysprotect.receitasmedicas.model.AppUser;
import com.sysprotect.receitasmedicas.repository.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository userRepository) {
        this.appUserRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuário não encontrado: " + username));

        Set<SimpleGrantedAuthority> authorities = user.getRoles()
                .stream()
                .filter(role -> role.isEnabled())
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.isEnabled())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .authorities(authorities)
                .build();
    }
}
