package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.ResetPasswordDTO;
import com.project.tour_booking.DTO.SignInDTO;
import com.project.tour_booking.DTO.SignUpDTO;
import com.project.tour_booking.Service.User.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticateController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody SignInDTO signInDTO, HttpServletResponse response) {
        return ResponseEntity.ok(userService.signIn(signInDTO, response));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO) {
        return ResponseEntity.ok(userService.signUp(signUpDTO));
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
