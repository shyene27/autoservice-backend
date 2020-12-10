package com.gideon.autoservice.services;

import com.gideon.autoservice.dao.UserRepository;
import com.gideon.autoservice.entities.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;


@Component
public class LoggedUserValidationService {

    @Autowired
    UserRepository userRepository;


    public void validateUserAccess(String requestedEmail) throws AccessDeniedException {

        if ( isCurrentUserAdmin() || isTheSameUser(requestedEmail) ){
            return;
        }

        throw new AccessDeniedException("Access Denied!");

    }

    public void validateUserAccess(Car car) throws AccessDeniedException{

        validateUserAccess(userRepository
                .findById(car.getUser().getUserId())
                .get()
                .getUserEmail());

    }

    public boolean isCurrentUserAdmin(){
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .toString()
                .contains("[ADMIN]");
    }

    private boolean isTheSameUser(String requestedEmail) {
        return requestedEmail.equals(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
    }

}
