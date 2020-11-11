package com.gideon.autoservice.dao;

import com.gideon.autoservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    public List<User> findByUserName(String userName);


}


