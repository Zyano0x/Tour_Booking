package com.project.tour_booking.Service;

import com.project.tour_booking.Entity.SecureToken;

public interface SecureTokenService {
    SecureToken findByToken(String token);
}
