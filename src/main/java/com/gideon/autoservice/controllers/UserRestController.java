package com.gideon.autoservice.controllers;

import com.gideon.autoservice.dao.UserDao;
import com.gideon.autoservice.entity.User;
import com.gideon.autoservice.exceptions.UserNotFoundException;
import com.gideon.autoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/user-management")
public class UserRestController {

    public static final String MESSAGE_NO_USER_BY_NAME = "No resource found for User Name (%s)";
    public static final String MESSAGE_NO_USER_BY_ID = "No User found for id (%s)";
    public static final String MESSAGE_USER_CREATED = "User created successfully";
    public static final String MESSAGE_USER_EDITED = "User with Id: %s edited successfully";
    public static final String MESSAGE_USER_DELETED = "User with Id: %s deleted successfully";


    @Autowired
    UserService userService;


    @GetMapping("/users")
    public List<User> getAllUsers() {

        return userService.findAll();
    }


    @GetMapping("/users/name/{userName}")
    public List<User> getUserByUserName(@PathVariable("userName") String userName) {

        try {
            return userService.getUserByUserName(userName);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_BY_NAME, userName));
        }

    }


    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {

        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_BY_ID, id));
        }
    }


    @PostMapping("/users")
    public Map<String, String> saveUser(@RequestBody User theUser) {
        Long id = theUser.getUserId();
        userService.save(theUser);
        return Map.of("message", String.format(MESSAGE_USER_CREATED));
    }


    @PatchMapping("/users")
    public Map<String, String> editUser(@RequestBody User theUser) {
        final Long id = theUser.getUserId();
        try {
            userService.editUser(theUser);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_BY_ID, id));
        }
        return Map.of("message", String.format(MESSAGE_USER_EDITED, id));
    }

    @DeleteMapping("/users/{id}")
    public Map<String, String> deleteUserById(@PathVariable Long id) {

        try {
            userService.deleteUserById(id);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_BY_ID, id));
        }
        return Map.of("message", String.format(MESSAGE_USER_DELETED, id));
    }
}
