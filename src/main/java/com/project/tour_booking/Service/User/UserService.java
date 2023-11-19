package com.project.tour_booking.Service.User;

import org.springframework.http.ResponseEntity;

import com.project.tour_booking.DTO.ResetPasswordDTO;
import com.project.tour_booking.DTO.SignUpDTO;

public interface UserService {
    ResponseEntity<String> signIn(String usernameOrEmail, String password);

    ResponseEntity<String> signUp(SignUpDTO signUpDTO);

    ResponseEntity<String> confirmEmailVerification(String confirmationToken);

    ResponseEntity<String> resendEmailVerification(String oldToken);

    ResponseEntity<String> forgotPassword(String email);

    ResponseEntity<String> resetPassword(ResetPasswordDTO resetPasswordDTO);

    ResponseEntity<String> updateUserRole(String username, Long roleId);

    ResponseEntity<String> updateUserAccount(String email);
}
