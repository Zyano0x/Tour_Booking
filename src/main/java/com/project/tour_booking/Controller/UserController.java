package com.project.tour_booking.Controller;

import com.project.tour_booking.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users-by-id")
    public ResponseEntity<?> getUserById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
