package com.gideon.autoservice.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class LoggedUserValidationService {


    public boolean hasUserEmail(String requestedEmail) {

        Collection<? extends GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String role = roles.toString();

        if (role.contains("[ADMIN]")) return true;

        else {
            String loggedEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            return requestedEmail.equals(loggedEmail);
        }
    }
}
