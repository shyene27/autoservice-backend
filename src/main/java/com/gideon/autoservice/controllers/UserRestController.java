package com.gideon.autoservice.controllers;

import com.gideon.autoservice.entities.UserDto;
import com.gideon.autoservice.exceptions.UserAlreadyExistsException;
import com.gideon.autoservice.exceptions.UserNotFoundException;
import com.gideon.autoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
public class UserRestController {

    public static final String MESSAGE_NO_USER_BY_ID = "No User found for id (%s)";
    public static final String MESSAGE_ACCESS_DENIED = "Not authorised!";
    public static final String MESSAGE_USER_EDITED = "User with Id: %s edited successfully";
    public static final String MESSAGE_USER_DELETED = "User with Id: %s deleted successfully";


    @Autowired
    UserService userService;

    @Autowired
    TokenStore tokenStore;

    @GetMapping("/")
    public List<UserDto> findAll() {

        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {

        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_BY_ID, id));
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(FORBIDDEN, String.format(MESSAGE_ACCESS_DENIED));
        }
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        UserDto createdUser;

        try {
            createdUser = userService.save(userDto);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(CONFLICT);
        }

        return new ResponseEntity<>(createdUser, CREATED);
    }

    @PatchMapping("/{id}")
    public UserDto editUser(@RequestBody UserDto userDto) {
        try {
            return userService.editUser(userDto);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_BY_ID, userDto.getId()));
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(FORBIDDEN, String.format(MESSAGE_ACCESS_DENIED));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {

        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>(NO_CONTENT);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_BY_ID, id));
        }

    }

    @GetMapping("/register/{token}")
    public ResponseEntity<Object> confirmUserAccount(@PathVariable String token) {

        try {
            userService.confirmUserAccount(token);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        return new ResponseEntity<>(ACCEPTED);

    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);

            tokenStore.removeAccessToken(accessToken);

        }
    }

    //this endpoint is for test purposes
    @GetMapping("/test/{id}")
    public Collection<? extends GrantedAuthority> testLoggedUser(@PathVariable Long id) {
        Collection<? extends GrantedAuthority> userEmail = SecurityContextHolder.getContext().getAuthentication().getAuthorities();


        return userEmail;
    }

}

