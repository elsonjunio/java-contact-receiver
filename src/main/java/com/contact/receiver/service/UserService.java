package com.contact.receiver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.contact.receiver.entity.AppUser;
import com.contact.receiver.entity.Role;
import com.contact.receiver.repository.RoleRepository;
import com.contact.receiver.repository.UserRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecurityService securityService;

    public AppUser saveUser(AppUser user) {

        user.setRoles(
                user.getRoles().stream()
                        .map(role -> {
                            Role foundRole = roleRepository.findByName(role.getName());
                            if (foundRole == null) {
                                throw new IllegalArgumentException("Role not found: " + role.getName());
                            }
                            return foundRole;
                        })
                        .toList());

        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }

    public AppUser applyUpdate(AppUser existingUser, AppUser patchedUser) {

        boolean isAdmin = securityService.isAuthenticatedUserAdmin();
        String currentUsername = securityService.getAuthenticatedUsername();

        // ⛔️ Validações obrigatórias
        if (patchedUser.getUsername() == null || patchedUser.getUsername().isBlank()) {
            throw new IllegalArgumentException("The username must be defined.");
        }

        if (patchedUser.getRoles() == null || patchedUser.getRoles().isEmpty()) {
            throw new IllegalArgumentException("At least one role must be defined.");
        }

        // ✅ Verifica se pode alterar: ADMIN ou o próprio usuário
        boolean isSameUser = currentUsername.equals(existingUser.getUsername());
        if (!isAdmin && !isSameUser) {
            throw new AccessDeniedException("You do not have permission to modify this user.");
        }

        // ⛔️ Não permite alterar roles se não for admin
        if (!isAdmin && !patchedUser.getRoles().equals(existingUser.getRoles())) {
            throw new AccessDeniedException("You do not have permission to change roles for this user.");
        }

        // ✅ Atualiza username apenas se diferente
        if (!patchedUser.getUsername().equals(existingUser.getUsername())) {
            existingUser.setUsername(patchedUser.getUsername());
        }

        // ✅ Atualiza roles somente se for admin
        if (isAdmin) {
            List<Role> validatedRoles = patchedUser.getRoles().stream()
                    .map(role -> {
                        Role found = roleRepository.findByName(role.getName());
                        if (found == null) {
                            throw new IllegalArgumentException("Role not found: " + role.getName());
                        }
                        return found;
                    })
                    .collect(Collectors.toCollection(ArrayList::new));
            existingUser.setRoles(validatedRoles);
        }

        // ✅ Sempre atualiza a senha
        existingUser.setPassword(patchedUser.getPassword());

        return userRepository.save(existingUser);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<AppUser> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public AppUser getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
