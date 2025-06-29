package com.contact.receiver.mapper;

import org.springframework.stereotype.Component;

import com.contact.receiver.dto.ContactRequestDTO;
import com.contact.receiver.dto.ContactResponseDTO;
import com.contact.receiver.entity.Contact;
import com.contact.receiver.service.UserService;


@Component
public class ContactMapper {

    private final UserService userService;
    
    public ContactMapper(UserService userService) {
        this.userService = userService;
    }

    public Contact toEntity(ContactRequestDTO dto) {

        Contact c = Contact.builder()
        .firstName(dto.getFirstName())
        .surName(dto.getSurName())
        .contactNumber(dto.getContactNumber())
        .email(dto.getEmail())
        .owner(userService.getUserById(dto.getOwnerId()))
        .build();


        return c;
    }

    public static ContactResponseDTO toDto(Contact entity) {

        ContactResponseDTO c = ContactResponseDTO.builder()
        .contactNumber(entity.getContactNumber())
        .created_at(entity.getCreated_at())
        .updated_at(entity.getUpdated_at())
        .email(entity.getEmail())
        .ownerID(entity.getOwner().getId())
        .firstName(entity.getFirstName())
        .surName(entity.getSurName())
        .id(entity.getId())
        .build();
        return c;
    }
}
