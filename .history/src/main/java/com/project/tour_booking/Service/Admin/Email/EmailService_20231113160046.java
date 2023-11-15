package com.project.tour_booking.Service.Admin;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    public void sendEmail(SimpleMailMessage eMailMessage);
}
