package com.contact.receiver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contact.receiver.entity.Role;
import com.contact.receiver.permissions.RoleEnum;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByName(RoleEnum name);
    List<Role> findByNameIn(List<RoleEnum> names);

}
