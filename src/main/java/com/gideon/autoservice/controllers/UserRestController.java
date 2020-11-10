package com.gideon.autoservice.controllers;

import com.gideon.autoservice.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World!";
    }

    @GetMapping("/users/{userId}")
    public User getUsers(@PathVariable int userId){

        List<User> theUsers = new ArrayList<>();
        theUsers.add(new User("Alexei","Cutasevici"));
        theUsers.add(new User("vase","botnaru"));
        theUsers.add(new User("roman","manaf"));

        return theUsers.get(userId);
    }
}
