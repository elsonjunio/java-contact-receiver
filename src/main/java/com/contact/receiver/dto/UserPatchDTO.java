package com.contact.receiver.dto;

import java.util.List;

import com.contact.receiver.permissions.RoleEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPatchDTO {
    private String username;
    private String password;
    private List<RoleEnum> roles;
}
