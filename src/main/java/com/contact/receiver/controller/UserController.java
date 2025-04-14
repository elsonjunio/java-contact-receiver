package com.contact.receiver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contact.receiver.entity.AppUser;
import com.contact.receiver.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody AppUser user) {

        AppUser createdUser = userService.saveUser(user);

        return new ResponseEntity<String>("Created user: " + createdUser.getUsername(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody AppUser user) {
        AppUser updatedUser = userService.saveUser(user);
        return new ResponseEntity<String>("Created user: " + updatedUser.getUsername(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> getAllUsers() {
        
        return new ResponseEntity<List<AppUser>>(userService.getAllUsers(), HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteUser(@RequestBody AppUser user){
        userService.deleteUser(user);
    }

}
