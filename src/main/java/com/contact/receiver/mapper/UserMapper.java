package com.contact.receiver.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.contact.receiver.dto.RoleDTO;
import com.contact.receiver.dto.UserPatchDTO;
import com.contact.receiver.dto.UserRequestDTO;
import com.contact.receiver.dto.UserResponseDTO;
import com.contact.receiver.entity.AppUser;
import com.contact.receiver.entity.Role;

@Component
public class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO toResponseDTO(AppUser user) {
        return UserResponseDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .roles(user.getRoles().stream().map(role ->
                RoleDTO.builder()
                    .id(role.getId())
                    .name(role.getName())
                    .build()
            ).collect(Collectors.toList()))
            .build();
    }

    public AppUser toEntity(UserRequestDTO dto, List<Role> roleEntities) {
        AppUser user = new AppUser();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(roleEntities);
        return user;
    }

    public AppUser patchEntity(UserPatchDTO dto, List<Role> roleEntities, AppUser user) {
        AppUser patchedUser = new AppUser();
       
        if (dto.getUsername() != null) {
            patchedUser.setUsername(dto.getUsername());
        } else {
            patchedUser.setUsername(user.getUsername());
        }

        if (dto.getPassword() != null) {
            patchedUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        } else {
            patchedUser.setPassword(user.getPassword());
        }

        if (dto.getRoles() != null) {
            patchedUser.setRoles(roleEntities);
        } else {
            patchedUser.setRoles(user.getRoles());
        }

        return patchedUser;
    }
}
