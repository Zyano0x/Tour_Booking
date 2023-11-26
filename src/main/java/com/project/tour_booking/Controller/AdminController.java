package com.project.tour_booking.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin("*")
@RequestMapping("/panel")
public class AdminController {

    @GetMapping("/dashboard")
    public String Dashboard() {
        return "admin/index";
    }

    @GetMapping("/login")
    public String LoginPage() {
        return "admin/login";
    }

}
