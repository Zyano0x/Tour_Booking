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
public class AdminPageController {
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

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        return "admin/index";
    }

    @GetMapping("/users-manage")
    public String userManage(Model model) {
        return "admin/user-manage";
    }

    @GetMapping("/tours-manage")
    public String tourManage(Model model) {
        return "admin/tour-manage";
    }

    @GetMapping("/tour-types-manage")
    public String tourTypeManage(Model model) {
        return "admin/tour-type-manage";
    }

    @GetMapping("/destinations-manage")
    public String destinationManage(Model model) {
        return "admin/destination-manage";
    }

    @GetMapping("/departures-manage")
    public String departureManage(Model model) {
        return "admin/departure-manage";
    }

    @GetMapping("/articles-manage")
    public String articlesManage(Model model) {
        return "admin/articles-manage";
    }

    @GetMapping("/bookings-manage")
    public String bookingManage(Model model) {
        return "admin/booking-manage";
    }
}