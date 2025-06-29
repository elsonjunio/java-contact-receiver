package com.contact.receiver.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public boolean isAuthenticatedUserAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(role -> role.equals("ADMIN") || role.equals("ROLE_ADMIN"));
    }

    public boolean canEditUser(String targetUsername) {
        String currentUsername = getAuthenticatedUsername();
        return isAuthenticatedUserAdmin() || targetUsername.equals(currentUsername);
    }
}
