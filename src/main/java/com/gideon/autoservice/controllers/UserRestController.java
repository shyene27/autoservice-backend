package com.gideon.autoservice.controllers;

import com.gideon.autoservice.entity.User;
import com.gideon.autoservice.exceptions.UserAlreadyExistsException;
import com.gideon.autoservice.exceptions.UserNotFoundException;
import com.gideon.autoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
public class UserRestController {

    public static final String MESSAGE_NO_USER_BY_NAME = "No resource found for User Name (%s)";
    public static final String MESSAGE_NO_USER_BY_ID = "No User found for id (%s)";
    public static final String MESSAGE_USER_CREATED = "User created successfully";
    public static final String MESSAGE_USER_EDITED = "User with Id: %s edited successfully";
    public static final String MESSAGE_USER_DELETED = "User with Id: %s deleted successfully";


    @Autowired
    UserService userService;

    @GetMapping("/")
    public List<User> getAllUsers() {

        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {

        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_BY_ID, id));
        }
    }

    @PostMapping("/")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User createdUser = user;

        try {
            userService.save(user);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(CONFLICT);
        }

        return new ResponseEntity<>(createdUser, CREATED);
    }

    @PutMapping("/{id}")
    public User editUser(@RequestBody User user) {
        try {
            return userService.editUser(user);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_BY_ID, user.getUserId()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {

        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(NO_CONTENT);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_BY_ID, id));
        }

    }

    @GetMapping("/register/{token}")
    public ResponseEntity<Object> confirmUserAccount(@PathVariable String token) {

        try {
            userService.confirmUserAccount(token);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        return new ResponseEntity<>(ACCEPTED);

    }
}

