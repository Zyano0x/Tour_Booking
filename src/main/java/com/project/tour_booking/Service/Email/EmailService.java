package com.project.tour_booking.Service.Email;

import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Entity.SecureToken;
import com.project.tour_booking.Entity.User;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendEmailVerify(User user, SecureToken token);
    void sendEmailForgotPassword(User user, SecureToken token);
    void sendEmailVerifyBooking(User user, Booking booking);
    void sendEmailUpdateBooking(User user, Booking booking);
    void sendEmailUpdateBookingStatus(User user, Booking booking, SecureToken token);
    void sendEmailConfirmBookingCancel(Booking booking, Long code);
}
