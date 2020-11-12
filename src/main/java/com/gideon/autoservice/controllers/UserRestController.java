package com.gideon.autoservice.controllers;

import com.gideon.autoservice.dao.UserDao;
import com.gideon.autoservice.entity.User;
import com.gideon.autoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user-management")
public class UserRestController {

    public static final String MESSAGE_NO_USER_BY_NAME = "No resource found for User Name (%s)";
    public static final String MESSAGE_NO_USER_BY_ID = "No User found for id (%s)";
    public static final String MESSAGE_USER_CREATED = "User with Id: %s created successfully";
    public static final String MESSAGE_USER_EDITED = "User with Id: %s edited successfully";
    public static final String MESSAGE_USER_DELETED = "User with Id: %s deleted successfully";


    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;


    @GetMapping("/users")
    public List<User> getAllUsers() {

        return userService.findAll();
    }


    @GetMapping("/users/name/{userName}")
    public List<User> getUserByUserName(@PathVariable("userName") String userName) {

        return userService.getUserByUserName(userName);
    }


    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {

        return userService.getUserById(id);
    }


    @PostMapping("/users")
    public Map<String, String> saveUser(@RequestBody User theUser) {

        return userService.save(theUser);
    }


    @PatchMapping("/users")
    public Map<String, String> editUser(@RequestBody User theUser) {

        return userService.editUser(theUser);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, String> deleteUserById(@PathVariable Long id) {

        return userService.deleteUserById(id);
    }
}
