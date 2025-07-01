package com.contact.receiver.service;

import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    
    private Object getPrincipal() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    public String getAuthenticatedUsername() {
        Object principal = getPrincipal();

        String username;
        if (principal instanceof Map) {
            username = (String) ((Map<?, ?>) principal).get("sub");
        } else if (principal instanceof String) {
            username = (String) principal;
        } else {
            throw new RuntimeException("Unsupported principal type: " + principal.getClass());
        }

        return username;

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
