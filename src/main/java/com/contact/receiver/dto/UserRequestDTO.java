package com.contact.receiver.dto;

import java.util.List;

import com.contact.receiver.permissions.RoleEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    @NotBlank(message = "{invalid.username}")
    @Size(min = 3, max = 20, message = "{invalid.username.size}")
    private String username;

    private String password;

    private List<RoleEnum> roles;
}
