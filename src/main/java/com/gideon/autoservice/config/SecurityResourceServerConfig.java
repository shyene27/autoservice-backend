package com.gideon.autoservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

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
                .antMatchers(HttpMethod.GET, "/users/").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/{id}").hasAnyAuthority("ADMIN", "MECHANIC", "CUSTOMER")
                .antMatchers(HttpMethod.GET, "/users/register/{token}").permitAll()
                .antMatchers(HttpMethod.GET, "/users/reset/").permitAll()
                .antMatchers(HttpMethod.POST, "/users/reset/{token}").permitAll()
                .antMatchers(HttpMethod.POST, "/users/").permitAll()
                .antMatchers(HttpMethod.PATCH, "/users/{id}").hasAnyAuthority("ADMIN", "MECHANIC", "CUSTOMER")
                .antMatchers(HttpMethod.DELETE, "/users/{id}").hasAnyAuthority("ADMIN")

                .antMatchers(HttpMethod.GET, "/cars/").permitAll()
                .antMatchers(HttpMethod.GET, "/cars/{id}").hasAnyAuthority("ADMIN", "MECHANIC", "CUSTOMER")
                .antMatchers(HttpMethod.POST, "/cars/").permitAll()
                .antMatchers(HttpMethod.PATCH, "/cars/").permitAll()
                .antMatchers(HttpMethod.DELETE, "/cars/{id}").permitAll()


                .anyRequest().authenticated();


    }
}
