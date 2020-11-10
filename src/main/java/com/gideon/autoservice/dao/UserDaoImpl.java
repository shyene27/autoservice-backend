package com.gideon.autoservice.dao;

import com.gideon.autoservice.config.UserRowMapper;
import com.gideon.autoservice.entity.User;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    public UserDaoImpl (NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    NamedParameterJdbcTemplate template;

    @Override
    public List<User> findAll() {
        return template.query("select * from autoservice.users", new UserRowMapper());
    }
}
