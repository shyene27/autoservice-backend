package com.gideon.autoservice.services;

import com.gideon.autoservice.entity.User;
import java.util.List;
import java.util.Optional;


public interface UserService {

    public List<User> findAll();


    public List<User> getUserByUserName(String userName);

    public Optional<User> getUserById(Long id);

    void save(User theUser);

    void deleteUserById(Long id);
}
