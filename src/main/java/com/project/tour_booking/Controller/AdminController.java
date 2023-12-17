package com.project.tour_booking.Controller;

import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @ModelAttribute("id")
    public Long userId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = userService.user(userDetails.getUsername());

            if (user != null) {
                return user.getId();
            }
        }

        return null;
    }

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

    @GetMapping("/destination-manage")
    public String destinationManage(Model model) {
        model.addAttribute("path", "/panel/destination-manage");
        return "admin/destination-manage";
    }

    @GetMapping("/departure-manage")
    public String departureManage(Model model) {
        model.addAttribute("path", "/panel/departure-manage");
        return "admin/departure-manage";
    }

    @GetMapping("/articles-manage")
    public String articlesManage(Model model) {
        model.addAttribute("path", "/panel/articles-manage");
        return "admin/articles-manage";
    }

    @GetMapping("/booking-manage")
    public String bookingManage(Model model) {
        model.addAttribute("path", "/panel/booking-manage");
        return "admin/booking-manage";
    }
}