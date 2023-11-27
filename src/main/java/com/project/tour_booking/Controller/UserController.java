package com.project.tour_booking.Controller;

import com.project.tour_booking.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
