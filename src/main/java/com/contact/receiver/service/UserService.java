package com.contact.receiver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.contact.receiver.entity.AppUser;
import com.contact.receiver.entity.Role;
import com.contact.receiver.repository.IRoleRepository;
import com.contact.receiver.repository.IUserRepository;

@Service
public class UserService {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IRoleRepository iRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser saveUser(AppUser user) {

        user.setRoles(
                user.getRoles().stream()
                        .map(role -> {
                            Role foundRole = iRoleRepository.findByName(role.getName());
                            if (foundRole == null) {
                                throw new IllegalArgumentException("Role not found: " + role.getName());
                            }
                            return foundRole;
                        })
                        .toList());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.iUserRepository.save(user);
    }

    public AppUser updateUser(AppUser user) {

        user.setRoles(
                user.getRoles().stream()
                        .map(role -> {
                            Role foundRole = iRoleRepository.findByName(role.getName());
                            if (foundRole == null) {
                                throw new IllegalArgumentException("Role not found: " + role.getName());
                            }
                            return foundRole;
                        })
                        .toList());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.iUserRepository.save(user);
    }

    public void deleteUser(AppUser user) {
        this.iUserRepository.deleteById(user.getId());
    }

    public List<AppUser> getAllUsers() {
        return this.iUserRepository.findAll();
    }

}
