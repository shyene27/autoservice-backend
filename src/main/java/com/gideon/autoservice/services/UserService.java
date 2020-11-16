package com.gideon.autoservice.services;

import com.gideon.autoservice.dao.ConfirmationTokenDao;
import com.gideon.autoservice.dao.UserDao;
import com.gideon.autoservice.entity.ConfirmationToken;
import com.gideon.autoservice.entity.User;
import com.gideon.autoservice.exceptions.UserAlreadyExistsException;
import com.gideon.autoservice.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {


    @Autowired
    UserDao userDao;
    @Autowired
    ConfirmationTokenDao confirmationTokenDao;
    @Autowired
    EmailSenderService emailSenderService;

    public List<User> findAll() {

        return userDao.findAll();
    }

    public Optional<User> getUserById(Long id) throws UserNotFoundException {
        userDao.findById(id).orElseThrow(() -> new UserNotFoundException());
        return userDao.findById(id);
    }

    public User save(User user) throws UserAlreadyExistsException {

        userDao.findByUserEmail(user.getUserEmail()).ifPresent(s -> {
            throw new UserAlreadyExistsException();
        });

        User createdUser = userDao.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(createdUser);
        confirmationTokenDao.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(createdUser.getUserEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("sergiucr40@gmail.com");
        mailMessage.setText("To confirm your email and finish your registration please click here: " +
                "http://localhost:8080/users/register/" + confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);


        return createdUser;
    }

    public void confirmUserAccount(String tokenString) throws UserNotFoundException {

        ConfirmationToken token = confirmationTokenDao.findByConfirmationToken(tokenString).orElseThrow(() -> new UserNotFoundException());

        User user = userDao.findByUserEmail(token.getUser().getUserEmail()).get();
        user.setEnabled(true);
        userDao.save(user);
    }


    public User editUser(@RequestBody User user) throws UserNotFoundException {
        userDao.findById(user.getUserId()).orElseThrow(() -> new UserNotFoundException());
        return userDao.save(user);
    }


    public void deleteUserById(Long id) throws UserNotFoundException {

        userDao.findById(id).orElseThrow(() -> new UserNotFoundException());
        userDao.deleteById(id);
    }

}
