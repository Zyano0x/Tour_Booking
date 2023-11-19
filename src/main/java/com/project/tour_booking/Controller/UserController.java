package com.project.tour_booking.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.DTO.ResetPasswordDTO;
import com.project.tour_booking.DTO.SignInDTO;
import com.project.tour_booking.DTO.SignUpDTO;
import com.project.tour_booking.DTO.UpdateUserRoleDTO;
import com.project.tour_booking.Service.User.UserService;

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

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPasswordUserAccount(@RequestBody String email) {
        return userService.forgotPassword(email);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPasswordUserAccount(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return userService.resetPassword(resetPasswordDTO);
    }

    @PutMapping("/admin/update-role/{username}")
    public ResponseEntity<String> updateUserRole(@PathVariable String username, @RequestBody UpdateUserRoleDTO updateUserRoleDTO) {
        return userService.updateUserRole(username, updateUserRoleDTO.getRoleId());
    }

    @PutMapping("/admin/update-account")
    public ResponseEntity<String> lockUserAccount(@RequestBody String email) {
        return userService.updateUserAccount(email);
    }
}
