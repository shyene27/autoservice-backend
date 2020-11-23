package com.gideon.autoservice.services;

import com.gideon.autoservice.config.translator.UserTranslator;
import com.gideon.autoservice.dao.ConfirmationTokenDao;
import com.gideon.autoservice.dao.UserRepository;
import com.gideon.autoservice.entity.ConfirmationToken;
import com.gideon.autoservice.entity.User;
import com.gideon.autoservice.entity.UserDto;
import com.gideon.autoservice.exceptions.UserAlreadyExistsException;
import com.gideon.autoservice.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {


    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ConfirmationTokenDao confirmationTokenDao;
    @Autowired
    private final EmailSenderService emailSenderService;


    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            userDtos.add(UserTranslator.toDto(user));
        }

        return userDtos;
    }


    public UserDto getUserById(Long id) throws UserNotFoundException {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

        UserDto userDto = UserTranslator.toDto(userRepository.findById(id).get());
        return userDto;
    }


    // getting email for the userDetails set of data for login
    public User getUserByEmail(String email) {
        return userRepository.findByUserEmail(email).get();
    }


    public UserDto save(UserDto userDto) throws UserAlreadyExistsException {

        User createdUser = UserTranslator.fromDtoCreate(userDto);

        userRepository.findByUserEmail(createdUser.getUserEmail()).ifPresent(s -> {
            throw new UserAlreadyExistsException();
        });

        userRepository.save(createdUser);

        ConfirmationToken confirmationToken = new ConfirmationToken(createdUser);
        confirmationTokenDao.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(createdUser.getUserEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("sergiucr40@gmail.com");
        mailMessage.setText("To confirm your email and finish your registration please click here: " +
                "http://localhost:8080/users/register/" + confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);

        return UserTranslator.toDto(createdUser);
    }


    public void confirmUserAccount(String tokenString) throws UserNotFoundException {

        ConfirmationToken token = confirmationTokenDao.findByConfirmationToken(tokenString).orElseThrow(() -> new UserNotFoundException());

        User user = userRepository.findByUserEmail(token.getUser().getUserEmail()).get();
        user.setEnabled(true);
        userRepository.save(user);
    }


    public UserDto editUser(@RequestBody UserDto userDto) throws UserNotFoundException {
        User currentUser = userRepository.findById(userDto.getId()).orElseThrow(() -> new UserNotFoundException());
        User updatedUser = UserTranslator.fromDtoUpdate(userDto,currentUser);

        return UserTranslator.toDto(userRepository.save(updatedUser));
    }


    public void deleteUserById(Long id) throws UserNotFoundException {

        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        userRepository.deleteById(id);
    }


}
