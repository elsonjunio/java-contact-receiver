package com.contact.receiver.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter

public class ContactResponseDTO {

    private Long id;
    private String firstName;
    private String surName;
    private String contactNumber;
    private String email;
    private Long ownerID;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
