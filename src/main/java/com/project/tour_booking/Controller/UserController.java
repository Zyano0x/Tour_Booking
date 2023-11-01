package com.project.tour_booking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.DTO.SignInDTO;
import com.project.tour_booking.DTO.SignUpDTO;
import com.project.tour_booking.Service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;    

    @PostMapping("/signin")
    public ResponseEntity<String> loginUser(@RequestBody SignInDTO signInDTO) {
        return userService.signIn(signInDTO.getUsernameOrEmail(), signInDTO.getPassword());
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDTO signUpDTO) {
       return userService.signUp(signUpDTO);
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return userService.confirmEmailVerification(confirmationToken);
    }

    @RequestMapping(value = "/resend-verification", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> reconfirmUserAccount(@RequestParam("token")String oldToken) {
        return userService.resendEmailVerification(oldToken);
    }
}
