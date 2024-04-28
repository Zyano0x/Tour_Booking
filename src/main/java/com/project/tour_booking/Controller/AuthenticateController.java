package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.*;
import com.project.tour_booking.Service.User.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticateController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody SignInDTO signInDTO, HttpServletResponse response) {
        return new ResponseEntity<>(userService.signIn(signInDTO, response), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody SignUpDTO signUpDTO) {
        return new ResponseEntity<>(userService.signUp(signUpDTO), HttpStatus.CREATED);
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        return userService.confirmEmailVerification(confirmationToken);
    }

    @GetMapping("/resend-verification")
    public ResponseEntity<?> reconfirmUserAccount(@RequestParam("token") String oldToken) {
        return userService.resendEmailVerification(oldToken);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPasswordUserAccount(@RequestBody String email) {
        return userService.forgotPassword(email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPasswordUserAccount(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return userService.resetPassword(resetPasswordDTO);
    }
}
