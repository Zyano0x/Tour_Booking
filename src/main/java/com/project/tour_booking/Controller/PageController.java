package com.project.tour_booking.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Service.User.UserService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class PageController {
    private UserService userService;

    @GetMapping
    public String home() {
        return "user/index";
    }

    @GetMapping("tours")
    public String tours() {
        return "user/tours";
    }

    @GetMapping("tours/{tourId}")
    public String detail() {
        return "user/tour_details";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        return "user/cart";
    }

    @ModelAttribute("userId")
    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.user(userDetails.getUsername());

            if (user != null) {
                return user.getId();
            }
        }

        return null;
    }
}
