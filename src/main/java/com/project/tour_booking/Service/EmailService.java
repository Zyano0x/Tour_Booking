package com.project.tour_booking.Service;

import com.project.tour_booking.Entity.SecureToken;
import com.project.tour_booking.Entity.User;

public interface EmailService {
    public void sendEmailVerification(User user, SecureToken token);    
}
