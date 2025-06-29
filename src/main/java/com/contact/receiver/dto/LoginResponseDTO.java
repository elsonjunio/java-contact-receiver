package com.contact.receiver.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponseDTO {

    private String token;
    private String username;
    private List<String> roles;
    private long expiresAt; 
}
