package com.project.tour_booking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.DTO.SignInDTO;
import com.project.tour_booking.DTO.SignUpDTO;
import com.project.tour_booking.Service.AuthService;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;    

    @PostMapping("/signin")
    public ResponseEntity<String> loginUser(@RequestBody SignInDTO signInDTO) {
        try {
            Authentication authentication = authService.signIn(signInDTO.getUsernameOrEmail(), signInDTO.getPassword());
    
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);

        } catch (AuthenticationException e) {
            // Handle authentication failure, e.g., return an error response
            return new ResponseEntity<>("Authentication failed.", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDTO signUpDTO) {
        try {
            authService.signUp(signUpDTO);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            // Handle registration failure, e.g., return an error response
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (IllegalStateException e) {
            // Handle role not found, e.g., return an error response
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
