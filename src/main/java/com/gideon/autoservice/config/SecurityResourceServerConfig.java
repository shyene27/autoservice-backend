package com.gideon.autoservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableResourceServer
public class SecurityResourceServerConfig extends ResourceServerConfigurerAdapter {


    private static final String RESOURCE_ID = "resource-server-rest-api";
    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    private static final String SECURED_PATTERN = "/secured/**";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(GET, "/users/").hasAnyAuthority("ADMIN")
                .antMatchers(GET, "/users/{id}").hasAnyAuthority("ADMIN", "MECHANIC", "CUSTOMER")
                .antMatchers(GET, "/users/register/{token}").permitAll()
                .antMatchers(GET, "/users/reset/").permitAll()
                .antMatchers(POST, "/users/reset/{token}").permitAll()
                .antMatchers(POST, "/users/").permitAll()
                .antMatchers(PATCH, "/users/{id}").hasAnyAuthority("ADMIN", "MECHANIC", "CUSTOMER")
                .antMatchers(DELETE, "/users/{id}").hasAnyAuthority("ADMIN")

                .antMatchers(GET, "/cars/").permitAll()
                .antMatchers(GET, "/cars/{id}").hasAnyAuthority("ADMIN", "MECHANIC", "CUSTOMER")
                .antMatchers(POST, "/cars/").permitAll()
                .antMatchers(PATCH, "/cars/").permitAll()
                .antMatchers(DELETE, "/cars/{id}").permitAll()

                .antMatchers(GET, "/orders/").permitAll()
                .antMatchers(POST, "/orders/").permitAll()


                .anyRequest().authenticated();


    }
}
