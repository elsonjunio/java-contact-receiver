package com.contact.receiver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contact.receiver.entity.Role;
import com.contact.receiver.permissions.RoleEnum;


@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>{
    Role findByName(RoleEnum name);

}
