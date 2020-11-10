package com.gideon.autoservice.dao;

import com.gideon.autoservice.entity.User;
import java.util.List;


public interface UserDao {

    public List<User> findAll();

}
