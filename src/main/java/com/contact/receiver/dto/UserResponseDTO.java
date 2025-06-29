package com.contact.receiver.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String username;
    private List<RoleDTO> roles;
}
