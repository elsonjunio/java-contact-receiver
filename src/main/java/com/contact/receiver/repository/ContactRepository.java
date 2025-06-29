package com.contact.receiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contact.receiver.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{

}
