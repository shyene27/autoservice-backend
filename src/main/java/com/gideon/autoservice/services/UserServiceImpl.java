package com.gideon.autoservice.services;

import com.gideon.autoservice.dao.UserDao;
import com.gideon.autoservice.dao.UserDaoImpl;
import com.gideon.autoservice.entity.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Resource
    UserDaoImpl userDao;

    @Override
    public List<User> findAll() {

        return userDao.findAll();
    }



}
