package com.pabloharrison.RomStorage.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class FilterSecurity extends OncePerRequestFilter {

    private final String adminApiKey;

    public FilterSecurity(
            @Value("${spring.security.admin-key}")
            String adminApiKey){
        this.adminApiKey = adminApiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestApiKey = request.getHeader("X-API-KEY");
        if(adminApiKey.equals(requestApiKey)){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    "rom-storage",
                    null,
                    Collections.singletonList(authority));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
