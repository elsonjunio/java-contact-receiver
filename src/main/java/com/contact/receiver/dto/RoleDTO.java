package com.contact.receiver.dto;

import com.contact.receiver.permissions.RoleEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RoleDTO {
    private Long id;
    private RoleEnum name;
}
