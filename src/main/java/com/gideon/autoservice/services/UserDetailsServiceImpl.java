package com.gideon.autoservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static com.gideon.autoservice.controllers.UserRestController.MESSAGE_USER_NOT_ENABLED;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, UserDeniedAuthorizationException {

        com.gideon.autoservice.entity.User user = userService.getUserByEmail(username);
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

        if (user.isEnabled()) return new User(user.getUserEmail(), user.getUserPassword(), Arrays.asList(authority));
        else throw new ResponseStatusException(UNAUTHORIZED, MESSAGE_USER_NOT_ENABLED);
    }
}
