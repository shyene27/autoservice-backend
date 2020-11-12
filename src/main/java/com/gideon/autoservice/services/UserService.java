package com.gideon.autoservice.services;

import com.gideon.autoservice.entity.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface UserService {

    List<User> findAll();

    List<User> getUserByUserName(String userName);

    Optional<User> getUserById(Long id);

    Map<String, String> save(User theUser);

    Map<String, String> editUser(@RequestBody User theUser);

    Map<String, String> deleteUserById(Long id);
}
