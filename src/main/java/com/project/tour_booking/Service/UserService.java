package com.project.tour_booking.Service;

import org.springframework.http.ResponseEntity;

import com.project.tour_booking.DTO.SignUpDTO;
import com.project.tour_booking.Service.UserServiceImpl.VerificationResult;

public interface UserService {
    ResponseEntity<String> signIn(String usernameOrEmail, String password);
    ResponseEntity<String> signUp(SignUpDTO signUpDTO);
    ResponseEntity<String> confirmEmailVerification(String confirmationToken);
    ResponseEntity<String> resendEmailVerification(String oldToken);
    VerificationResult validateToken(String confirmationToken);
}
