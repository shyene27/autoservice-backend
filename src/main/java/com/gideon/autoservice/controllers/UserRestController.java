package com.gideon.autoservice.controllers;

import com.gideon.autoservice.entity.User;
import com.gideon.autoservice.services.UserService;
import com.gideon.autoservice.services.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/autoservice")
public class UserRestController {

    @Resource
    UserService userService;

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World!";
    }

    @GetMapping("/userList")
    public List<User> getUsers(){
        return userService.findAll();
    }


}
