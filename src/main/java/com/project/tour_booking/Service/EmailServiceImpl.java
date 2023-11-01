package com.project.tour_booking.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.SecureToken;
import com.project.tour_booking.Entity.User;

@Service
public class EmailServiceImpl implements EmailService {
    
    @Autowired
    private JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    @Override
    public void sendEmailVerification(User user, SecureToken token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@tourbooking.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here: "
                            +"http://localhost:1337/api/confirm-account?token="+token.getToken());
        javaMailSender.send(mailMessage);
    }
}
