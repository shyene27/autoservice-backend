package com.gideon.autoservice.controllers;

import com.gideon.autoservice.entity.User;
import com.gideon.autoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-management")
public class UserRestController {



    @Autowired
    UserService userService;


    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/users/name/{userName}")
    public List<User> getUserByUserName(@PathVariable("userName") String userName){
       return userService.getUserByUserName(userName);
    }

    @GetMapping("/users/id/{id}")
    public Optional<User> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping("/save")
    public void saveUser(@RequestBody User theUser){
        userService.save(theUser);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
    }
}
