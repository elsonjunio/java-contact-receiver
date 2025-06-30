package com.contact.receiver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contact.receiver.entity.AppUser;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;


@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    Page<AppUser> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
