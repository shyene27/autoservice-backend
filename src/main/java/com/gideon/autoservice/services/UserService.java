package com.gideon.autoservice.services;

import com.gideon.autoservice.config.translators.UserTranslator;
import com.gideon.autoservice.dao.ConfirmationTokenDao;
import com.gideon.autoservice.dao.UserRepository;
import com.gideon.autoservice.entities.User;
import com.gideon.autoservice.entities.UserConfirmationToken;
import com.gideon.autoservice.dto.UserDto;
import com.gideon.autoservice.exceptions.UserAlreadyExistsException;
import com.gideon.autoservice.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.file.AccessDeniedException;
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
    private final LoggedUserValidationService validationService;

    public List<User> findAll() {

        return userRepository.findByIsNotExpired(true);
    }


    public User getUserById(Long id) throws UserNotFoundException, AccessDeniedException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        validationService.validateUserAccess(user.getUserEmail());

        return user;
    }

    // getting email for the userDetails set of data for login
    public User getUserByEmail(String email) throws UserNotFoundException {

        return userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UserNotFoundException());
    }



    public User save(User createdUser) throws UserAlreadyExistsException {

        userRepository.findByUserEmail(createdUser.getUserEmail()).ifPresent(s -> {
            throw new UserAlreadyExistsException();
        });

        userRepository.save(createdUser);

        UserConfirmationToken confirmationToken = new UserConfirmationToken(createdUser);
        confirmationTokenDao.save(confirmationToken);

        emailSenderService.sendRegisterEmail(createdUser.getUserEmail(), confirmationToken);

        return createdUser;
    }


    public void confirmUserAccount(String tokenString) throws UserNotFoundException {

        UserConfirmationToken token = confirmationTokenDao.findByConfirmationToken(tokenString).orElseThrow(() -> new UserNotFoundException());

        User user = userRepository.findByUserEmail(token.getUser().getUserEmail()).get();
        user.setEnabled(true);
        userRepository.save(user);
    }


    public User editUser(@RequestBody UserDto userDto) throws UserNotFoundException, AccessDeniedException {

        User currentUser = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new UserNotFoundException());

        validationService.validateUserAccess(currentUser.getUserEmail());

        User updatedUser = UserTranslator.fromDtoUpdate(userDto, currentUser);


        return userRepository.save(updatedUser);
    }


    public void deleteUserById(Long id) throws UserNotFoundException {

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        user.setNotExpired(false);
        userRepository.save(user);
    }


    public void resetPasswordInitiate(String userEmail) throws UserNotFoundException {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException());

        UserConfirmationToken confirmationToken = new UserConfirmationToken(user);
        confirmationTokenDao.save(confirmationToken);

        emailSenderService.sendResetEmail(userEmail, confirmationToken);
    }

    public void resetPasswordFinalize(String tokenString, String userPassword) throws UserNotFoundException{
        UserConfirmationToken token = confirmationTokenDao.findByConfirmationToken(tokenString).orElseThrow(() -> new UserNotFoundException());

        User user = userRepository.findByUserEmail(token.getUser().getUserEmail()).get();
        user.setUserPassword(userPassword);
        userRepository.save(user);
    }
}
