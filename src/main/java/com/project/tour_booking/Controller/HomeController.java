package com.project.tour_booking.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/login")
    public String LoginPage() {
        return "user/login";
    }

    @GetMapping("/register")
    public String RegisterPage() {
        return "user/register";
    }
}
