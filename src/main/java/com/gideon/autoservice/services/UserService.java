package com.gideon.autoservice.services;

import com.gideon.autoservice.entity.User;
import com.gideon.autoservice.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


public interface UserService {

    List<User> findAll();

    List<User> getUserByUserName(String userName) throws UserNotFoundException;

    Optional<User> getUserById(Long id) throws UserNotFoundException;

    void save(User theUser);

    void editUser(@RequestBody User theUser) throws UserNotFoundException;

    void deleteUserById(Long id) throws UserNotFoundException;
}
