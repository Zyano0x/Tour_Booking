package com.project.tour_booking.Service.Email;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    public void sendEmail(SimpleMailMessage eMailMessage);
}
