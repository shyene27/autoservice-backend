package com.gideon.autoservice.services;

import com.gideon.autoservice.dao.UserDao;
import com.gideon.autoservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {



    @Autowired
    UserDao userDao;

    @Override
    public List<User> findAll() {

//        return userDao.findByUserIdAndUserEmail(1L,"gideon.rainstorm@gmail.com");
        return userDao.findAll();
    }

    @Override
    public List<User> getUserByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public void save(User theUser){
        userDao.save(theUser);
    }

    @Override
    public void deleteUserById(Long id) {
        userDao.deleteById(id);
    }
}
