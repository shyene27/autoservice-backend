package com.gideon.autoservice.config.translators;

import com.gideon.autoservice.entities.User;
import com.gideon.autoservice.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserTranslator {

    @Autowired
    static
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static List<UserDto> toDtoList(List<User> users){

        return users.stream().map(UserTranslator::toDto).collect(Collectors.toList());
    }

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getUserId());
        userDto.setName(user.getFirstName() + " " + user.getLastName());
        userDto.setEmail(user.getUserEmail());
        userDto.setRole(user.getRole());

        return userDto;
    }

    public static User fromDtoCreate(UserDto userDto) {
        User user = new User();

        user.setUserId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setUserPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        return user;
    }

    public static User fromDtoUpdate(UserDto userDto, User currentUser){

        if (userDto.getEmail()!=null) currentUser.setUserEmail(userDto.getEmail());
        if (userDto.getRole()!=null) currentUser.setRole(userDto.getRole());
        if (userDto.getFirstName()!=null) currentUser.setFirstName(userDto.getFirstName());
        if (userDto.getLastName()!=null) currentUser.setLastName(userDto.getLastName());

        return currentUser;
    }
}
