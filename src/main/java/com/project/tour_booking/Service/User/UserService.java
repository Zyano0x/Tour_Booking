package com.project.tour_booking.Service.User;

import com.project.tour_booking.DTO.*;
import com.project.tour_booking.Entity.User;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService {

    AuthenticationResponse signIn(SignInDTO signInDTO, HttpServletResponse response);

    UserDTO signUp(SignUpDTO signUpDTO);

    ResponseEntity<?> confirmEmailVerification(String confirmationToken);

    ResponseEntity<?> resendEmailVerification(String oldToken);

    ResponseEntity<?> forgotPassword(String email);

    ResponseEntity<?> resetPassword(ResetPasswordDTO resetPasswordDTO);

    ResponseEntity<?> updateUserStatus(Long id);

    User user(String email);

    User getUserById(Long id);

    List<User> listUsers();
}
