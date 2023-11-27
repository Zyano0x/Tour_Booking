package com.project.tour_booking.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel")
public class AdminController {

    @GetMapping
    public String adminDashboard() {
        return "admin/index";
    }

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/user-manage")
    public String userManage() {
        return "admin/user-manage";
    }
}
