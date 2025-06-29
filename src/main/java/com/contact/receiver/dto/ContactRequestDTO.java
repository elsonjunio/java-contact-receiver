package com.contact.receiver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ContactRequestDTO {

    private String firstName;
    private String surName;
    private String contactNumber;
    private String email;
    private Long ownerId;
}
