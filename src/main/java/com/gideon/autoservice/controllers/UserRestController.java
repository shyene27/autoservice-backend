package com.gideon.autoservice.controllers;

import com.gideon.autoservice.config.translators.UserTranslator;
import com.gideon.autoservice.entities.User;
import com.gideon.autoservice.dto.UserDto;
import com.gideon.autoservice.exceptions.UserAlreadyExistsException;
import com.gideon.autoservice.exceptions.UserNotFoundException;
import com.gideon.autoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static com.gideon.autoservice.exceptions.HttpMessages.*;
import static com.gideon.autoservice.config.translators.UserTranslator.*;

@RestController
@RequestMapping("/users")
public class UserRestController {


    @Autowired
    UserService userService;
    @Autowired
    TokenStore tokenStore;
    @Autowired
    static
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/")
    public List<UserDto> findAll() {

        return UserTranslator.toDtoList(userService.findAll());
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {

        try {
            return UserTranslator.toDto(userService.getUserById(id));
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_FOUND));
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(FORBIDDEN, String.format(MESSAGE_ACCESS_DENIED));
        }
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto) {
        User createdUser;

        try {
            createdUser = userService.save(UserTranslator.fromDto(userDto));
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(CONFLICT);
        }
        return new ResponseEntity<>(UserTranslator.toDto(createdUser), CREATED);
    }

    @PatchMapping("/{id}")
    public UserDto editUser(@RequestBody UserDto userDto) {

        try {
            User modifiedUser = UserTranslator.fromDto(userDto);
            return UserTranslator.toDto(userService.editUser(modifiedUser));
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_FOUND));
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
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_FOUND));
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

    @GetMapping("/reset")
    public ResponseEntity<Object> resetPasswordInitiate(@RequestBody UserDto userDto){

        try{
            userService.resetPasswordInitiate(userDto.getEmail());
        } catch (UserNotFoundException e){
            throw new ResponseStatusException(NOT_FOUND, String.format(MESSAGE_NO_USER_FOUND));
        }

        return new ResponseEntity<>(NO_CONTENT);
    }

    @PostMapping("/reset/{token}")
    public ResponseEntity<Object> resetPasswordFinalize(@PathVariable String token, @RequestBody UserDto userDto){

        try{
            userService.resetPasswordFinalize(token, bCryptPasswordEncoder.encode(userDto.getPassword()));
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

