package com.project.tour_booking.Service.User;

import com.project.tour_booking.Entity.User;
import org.springframework.http.ResponseEntity;

import com.project.tour_booking.DTO.AuthenticationResponse;
import com.project.tour_booking.DTO.ResetPasswordDTO;
import com.project.tour_booking.DTO.SignInDTO;
import com.project.tour_booking.DTO.SignUpDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService {

    ResponseEntity<?> signIn(SignInDTO signInDTO, HttpServletResponse response);

    ResponseEntity<?> signUp(SignUpDTO signUpDTO);

    ResponseEntity<?> confirmEmailVerification(String confirmationToken);

    ResponseEntity<?> resendEmailVerification(String oldToken);

    ResponseEntity<?> forgotPassword(String email);

    ResponseEntity<?> resetPassword(ResetPasswordDTO resetPasswordDTO);

    ResponseEntity<?> updateUserStatus(String email);

    User user(String email);

    User getUserById(Long id);

    List<User> listUsers();
}
