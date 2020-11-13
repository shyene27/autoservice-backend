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
    public List<User> getUserByUserName(String userName) throws UserNotFoundException {

        List<User> theUsers = userDao.findByUserName(userName);
        if (theUsers.isEmpty()) throw new UserNotFoundException();

        return userDao.findByUserName(userName);
    }

    @Override
    public Optional<User> getUserById(Long id) throws UserNotFoundException {
        userDao.findById(id).orElseThrow(() -> new UserNotFoundException());
        return userDao.findById(id);
    }

    @Override
    public void save(User theUser) {
        userDao.save(theUser);
    }

    @Override
    public void editUser(@RequestBody User theUser) throws UserNotFoundException {
        final Long id = theUser.getUserId();
        userDao.findById(id).orElseThrow(() -> new UserNotFoundException());
        userDao.save(theUser);
    }


    @Override
    public void deleteUserById(Long id) throws UserNotFoundException {

        userDao.findById(id).orElseThrow(() -> new UserNotFoundException());
        userDao.deleteById(id);
    }
}
