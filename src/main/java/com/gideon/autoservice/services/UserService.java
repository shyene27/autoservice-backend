package com.gideon.autoservice.services;

import com.gideon.autoservice.config.translators.UserTranslator;
import com.gideon.autoservice.dao.ConfirmationTokenDao;
import com.gideon.autoservice.dao.UserRepository;
import com.gideon.autoservice.entities.UserConfirmationToken;
import com.gideon.autoservice.entities.User;
import com.gideon.autoservice.entities.UserDto;
import com.gideon.autoservice.exceptions.UserAlreadyExistsException;
import com.gideon.autoservice.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.file.AccessDeniedException;
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
    @Autowired
    private final LoggedUserValidationService loggedUserValidationService;

    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            userDtos.add(UserTranslator.toDto(user));
        }

        return userDtos;
    }


    public UserDto getUserById(Long id) throws UserNotFoundException, AccessDeniedException {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());

        UserDto userDto = UserTranslator.toDto(userRepository.findById(id).get());

        if (!loggedUserValidationService.hasUserEmail(userDto.getEmail()))
            throw new AccessDeniedException("Access Denied!");

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

        UserConfirmationToken confirmationToken = new UserConfirmationToken(createdUser);
        confirmationTokenDao.save(confirmationToken);

        emailSenderService.createEmail(createdUser.getUserEmail(), confirmationToken);

        return UserTranslator.toDto(createdUser);
    }


    public void confirmUserAccount(String tokenString) throws UserNotFoundException {

        UserConfirmationToken token = confirmationTokenDao.findByConfirmationToken(tokenString).orElseThrow(() -> new UserNotFoundException());

        User user = userRepository.findByUserEmail(token.getUser().getUserEmail()).get();
        user.setEnabled(true);
        userRepository.save(user);
    }


    public UserDto editUser(@RequestBody UserDto userDto) throws UserNotFoundException, AccessDeniedException {

        User currentUser = userRepository.findById(userDto.getId()).orElseThrow(() -> new UserNotFoundException());
        if (!loggedUserValidationService.hasUserEmail(currentUser.getUserEmail()))
            throw new AccessDeniedException("Access Denied!");

        User updatedUser = UserTranslator.fromDtoUpdate(userDto, currentUser);


        return UserTranslator.toDto(userRepository.save(updatedUser));
    }


    public void deleteUserById(Long id) throws UserNotFoundException {

        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        User user = userRepository.findById(id).get();
        user.setNotExpired(false);
        userRepository.save(user);
    }


}
