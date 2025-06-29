package com.contact.receiver.dto;

import java.util.List;

import com.contact.receiver.permissions.RoleEnum;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private List<RoleEnum> roles;
}
