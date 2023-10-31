package com.project.tour_booking.Service;

import org.springframework.security.core.Authentication;

import com.project.tour_booking.DTO.SignUpDTO;
import com.project.tour_booking.Entity.User;

public interface AuthService {
    Authentication signIn(String usernameOrEmail, String password);
    User signUp(SignUpDTO signUpDTO);
}
