package com.contact.receiver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.contact.receiver.dto.UserPatchDTO;
import com.contact.receiver.dto.UserRequestDTO;
import com.contact.receiver.dto.UserResponseDTO;
import com.contact.receiver.entity.AppUser;
import com.contact.receiver.entity.Role;
import com.contact.receiver.mapper.UserMapper;
import com.contact.receiver.service.RoleService;
import com.contact.receiver.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDTO> saveUser(@Valid @RequestBody UserRequestDTO dto) {

        List<Role> roles = roleService.findByNames(dto.getRoles());

        AppUser user = userMapper.toEntity(dto, roles);

        AppUser createdUser = userService.saveUser(user);

        UserResponseDTO userResponse = userMapper.toResponseDTO(createdUser);
        return new ResponseEntity<UserResponseDTO>(userResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<AppUser> page = userService.getAllUsers(pageable);

        Page<UserResponseDTO> response = page.map(userMapper::toResponseDTO);

        return new ResponseEntity<Page<UserResponseDTO>>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {

        AppUser user = userService.getUserById(id);

        UserResponseDTO userResponse = userMapper.toResponseDTO(user);

        return new ResponseEntity<UserResponseDTO>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO dto) {

        AppUser existingUser = userService.getUserById(id);

        List<Role> roles = roleService.findByNames(dto.getRoles());

        AppUser user = userMapper.toEntity(dto, roles);

        AppUser updatedUser = userService.applyUpdate(existingUser, user);

        UserResponseDTO userResponse = userMapper.toResponseDTO(updatedUser);

        return new ResponseEntity<UserResponseDTO>(userResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> patchUser(@PathVariable Long id, @RequestBody UserPatchDTO dto) {
        AppUser existingUser = userService.getUserById(id);
        List<Role> roles = roleService.findByNames(dto.getRoles());
        AppUser patchedUser = userMapper.patchEntity(dto, roles, existingUser);

        AppUser updatedUser = userService.applyUpdate(existingUser, patchedUser);

        UserResponseDTO userResponse = userMapper.toResponseDTO(updatedUser);

        return new ResponseEntity<UserResponseDTO>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<UserResponseDTO>> searchUsersByUsername(
            @RequestParam String username,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<AppUser> page = userService.searchUsersByUsername(username, pageable);

        Page<UserResponseDTO> response = page.map(userMapper::toResponseDTO);

        return new ResponseEntity<Page<UserResponseDTO>>(response, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser() {

        AppUser user = userService.getCurrentUser();

        UserResponseDTO userResponse = userMapper.toResponseDTO(user);

        return new ResponseEntity<UserResponseDTO>(userResponse, HttpStatus.OK);
    }

}
