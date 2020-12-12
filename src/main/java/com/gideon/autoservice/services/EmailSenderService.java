package com.gideon.autoservice.services;

import com.gideon.autoservice.entities.UserConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailSenderService")
public class EmailSenderService {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    public void sendRegisterEmail(String emailAdress, UserConfirmationToken confirmationToken){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAdress);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("sergiucr40@gmail.com");
        mailMessage.setText("To confirm your email and finish your registration please click here: " +
                "http://localhost:8080/users/register/" + confirmationToken.getConfirmationToken());

        sendEmail(mailMessage);
    }

    public void sendResetEmail(String emailAdress, UserConfirmationToken confirmationToken){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAdress);
        mailMessage.setSubject("Reset Password!");
        mailMessage.setFrom("sergiucr40@gmail.com");
        mailMessage.setText("To reset your password please click here: " +
                "http://localhost:8080/users/reset/" + confirmationToken.getConfirmationToken());

        sendEmail(mailMessage);
    }

    @Async
    public void sendEmail(SimpleMailMessage email){
        javaMailSender.send(email);
    }
}
