package com.gideon.autoservice.services;

import com.gideon.autoservice.dao.UserDao;
import com.gideon.autoservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import static com.gideon.autoservice.controllers.UserRestController.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class UserServiceImpl implements UserService {


    @Autowired
    UserDao userDao;

    @Override
    public List<User> findAll() {

        return userDao.findAll();
    }

    @Override
    public List<User> getUserByUserName(String userName) {

        List<User> theUsers = userDao.findByUserName(userName);
        if (theUsers.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_BY_NAME, userName));
        }

        return userDao.findByUserName(userName);
    }

    @Override
    public Optional<User> getUserById(Long id) {

        userDao.findById(id).orElseThrow(()
                -> new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_BY_ID, id)));

        return userDao.findById(id);
    }

    @Override
    public Map<String, String> save(User theUser) {

        userDao.save(theUser);
        Long id = theUser.getUserId();
        return Map.of("message", String.format(MESSAGE_USER_CREATED, id));
    }

    @Override
    public Map<String, String> editUser(@RequestBody User theUser) {
        final Long id = theUser.getUserId();
        userDao.findById(id).orElseThrow(()
                -> new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_BY_ID, id)));
        userDao.save(theUser);
        return Map.of("message", String.format(MESSAGE_USER_EDITED, id));
    }


    @Override
    public Map<String, String> deleteUserById(Long id) {

        if (!userDao.existsById(id)) {
            return Map.of("message", String.format(MESSAGE_NO_USER_BY_ID, id));
        }
        userDao.deleteById(id);
        return Map.of("message", String.format(MESSAGE_USER_DELETED, id));
    }
}
