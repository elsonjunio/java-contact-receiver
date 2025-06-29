package com.contact.receiver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contact.receiver.entity.Contact;
import com.contact.receiver.repository.ContactRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact saveContact(Contact contact){
        return contactRepository.save(contact);
    }

}
