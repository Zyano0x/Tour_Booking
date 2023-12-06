package com.project.tour_booking.Controller;

import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/admin/user")
    public ResponseEntity<?> user(@RequestParam String email) {
        return ResponseEntity.ok(userService.user(email));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> user(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/admin/list-users")
    public ResponseEntity<?> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @PutMapping("/admin/update-user-status")
    public ResponseEntity<?> updateUserStatus(@RequestBody String email) {
        return ResponseEntity.ok(userService.updateUserStatus(email));
    }
}
