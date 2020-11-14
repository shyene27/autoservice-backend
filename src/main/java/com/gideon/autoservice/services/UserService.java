package com.gideon.autoservice.services;

import com.gideon.autoservice.entity.User;
import com.gideon.autoservice.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface UserService {

    List<User> findAll();

    Optional<User> getUserById(Long id) throws UserNotFoundException;

    User save(User user);

    User editUser(@RequestBody User user) throws UserNotFoundException;

    void deleteUserById(Long id) throws UserNotFoundException;
}
