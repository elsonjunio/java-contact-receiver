package com.contact.receiver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.contact.receiver.entity.Role;
import com.contact.receiver.permissions.RoleEnum;
import com.contact.receiver.repository.RoleRepository;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findByNames(List<RoleEnum> names) {
        return roleRepository.findByNameIn(names);
    }


}
