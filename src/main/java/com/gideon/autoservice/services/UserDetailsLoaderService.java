package com.gideon.autoservice.services;

import com.gideon.autoservice.config.UserDetailsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsLoaderService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, UserDeniedAuthorizationException {

        com.gideon.autoservice.entities.User user = userService.getUserByEmail(username);

        return new UserDetailsConfig(user);
    }


}
