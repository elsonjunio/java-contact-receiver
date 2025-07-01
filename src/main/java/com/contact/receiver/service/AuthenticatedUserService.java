package com.contact.receiver.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.contact.receiver.entity.AppUser;
import com.contact.receiver.repository.UserRepository;

@Service
@Transactional
public class AuthenticatedUserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) {
        AppUser user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User " + username + "not found"));

        List<SimpleGrantedAuthority> roles = user.getRoles()
        .stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
        .collect(Collectors.toList());

        return new User(user.getUsername(), user.getPassword(), roles);
    }

}
