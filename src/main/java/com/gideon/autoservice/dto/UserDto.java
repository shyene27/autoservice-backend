package com.gideon.autoservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gideon.autoservice.enums.Role;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @JsonProperty("userId")
    private Long id;

    @NonNull
    private String email;
    @NonNull
    private Role role;
    @NonNull
    private String name;

    private String firstName;

    private String lastName;

    private String password;
}
