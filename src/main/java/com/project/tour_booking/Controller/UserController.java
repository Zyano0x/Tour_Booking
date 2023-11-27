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
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list-users")
    public ResponseEntity<?> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }
}
