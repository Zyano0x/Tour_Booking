package com.project.tour_booking.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel")
public class AdminController {

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("path", "/panel");
        return "admin/index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("path", "/login");
        return "admin/login";
    }

    @GetMapping("/user-manage")
    public String userManage(Model model) {
        model.addAttribute("path", "/panel/user-manage");
        return "admin/user-manage";
    }

    @GetMapping("/tour-manage")
    public String tourManage(Model model) {
        model.addAttribute("path", "/panel/tour-manage");
        return "admin/tour-manage";
    }
}