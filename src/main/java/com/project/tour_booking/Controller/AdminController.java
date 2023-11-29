package com.project.tour_booking.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel")
public class AdminController {

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("path", "/panel");
        return "admin/index";
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

    @GetMapping("/tour-type-manage")
    public String tourTypeManage(Model model) {
        model.addAttribute("path", "/panel/tour-type-manage");
        return "admin/tour-type-manage";
    }

    @GetMapping("/booking-manage")
    public String bookingManage(Model model) {
        model.addAttribute("path", "/panel/booking-manage");
        return "admin/booking-manage";
    }
}