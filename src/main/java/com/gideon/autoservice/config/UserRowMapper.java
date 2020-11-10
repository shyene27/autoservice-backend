package com.gideon.autoservice.config;

import com.gideon.autoservice.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<User> {


    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();

        user.setUserId(rs.getString("userid"));
        user.setUserName(rs.getString("username"));
        user.setUserPassword(rs.getString("userpassword"));
        user.setUserFirstName(rs.getString("userfirstname"));
        user.setUserLastName(rs.getString("userlastname"));
        user.setUserEmail(rs.getString("useremail"));

        return user;
    }
}
