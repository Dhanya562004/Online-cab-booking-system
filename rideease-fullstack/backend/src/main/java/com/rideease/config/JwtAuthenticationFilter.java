package com.rideease.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Claims claims = tokenProvider.getClaimsFromToken(jwt);
                Long userId = claims.get("id", Long.class);
                String username = claims.get("username", String.class);
                String role = claims.get("role", String.class);

                if (username != null) {
                    // Prepend "ROLE_" if it doesn't already have it
                    String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                    SimpleGrantedAuthority simpleAuthority = new SimpleGrantedAuthority(authority);

                    // Principal object is a UserPrincipal holding ID and Username
                    UserPrincipal principal = new UserPrincipal(userId, username, role);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            principal, null, Collections.singletonList(simpleAuthority));
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // A simple container to hold User info inside SecurityContext
    public static class UserPrincipal {
        private final Long id;
        private final String username;
        private final String role;

        public UserPrincipal(Long id, String username, String role) {
            this.id = id;
            this.username = username;
            this.role = role;
        }

        public Long getId() { return id; }
        public String getUsername() { return username; }
        public String getRole() { return role; }
    }
}
