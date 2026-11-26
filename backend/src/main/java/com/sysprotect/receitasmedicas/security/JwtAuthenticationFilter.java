package com.sysprotect.receitasmedicas.security;

import com.sysprotect.receitasmedicas.repository.AppUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AppUserRepository appUserRepository;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                                   AppUserRepository appUserRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        if (!jwtTokenProvider.validateToken(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Se já está autenticado, não re-autenticar
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        final String username = jwtTokenProvider.getUsername(jwt);

        if (username != null) {
            var userOptional = appUserRepository.findByUsername(username);

            if (userOptional.isPresent()) {
                var user = userOptional.get();

                List<String> roles = user.getRoles().stream()
                    .filter(role -> role.isEnabled())
                    .map(role -> role.getName())
                    .collect(Collectors.toList());

                UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .disabled(!user.isEnabled())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .authorities(roles.stream()
                        .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role))
                        .collect(Collectors.toSet())
                    )
                    .build();

                if (jwtTokenProvider.validateToken(jwt)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
