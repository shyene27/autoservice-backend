package com.gideon.autoservice.services;

import com.gideon.autoservice.dao.UserDao;
import com.gideon.autoservice.entity.User;
import com.gideon.autoservice.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {


    @Autowired
    UserDao userDao;

    @Override
    public List<User> findAll() {

        return userDao.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) throws UserNotFoundException {
        userDao.findById(id).orElseThrow(() -> new UserNotFoundException());
        return userDao.findById(id);
    }

    @Override
    public User save(User user) {
       return userDao.save(user);
    }

    @Override
    public User editUser(@RequestBody User user) throws UserNotFoundException {
        userDao.findById(user.getUserId()).orElseThrow(() -> new UserNotFoundException());
        return userDao.save(user);
    }


    @Override
    public void deleteUserById(Long id) throws UserNotFoundException {

        userDao.findById(id).orElseThrow(() -> new UserNotFoundException());
        userDao.deleteById(id);
    }
}
