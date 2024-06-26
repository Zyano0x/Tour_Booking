package com.project.tour_booking.Controller;

import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Service.User.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class UserPageController {
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
    public String tourDetail() {
        return "user/tour_details";
    }

    @GetMapping("/cart")
    public String cart() {
        return "user/cart";
    }

    @GetMapping("/articles")
    public String articles() {
        return "user/articles";
    }

    @GetMapping("/articles/{articleId}")
    public String articleDetail() {
        return "user/article_details";
    }

    @GetMapping("/register")
    public String register() {
        return "user/register";
    }

    @GetMapping("/booking-success")
    public String bookingSuccess() {
        return "user/booking_success";
    }

    @GetMapping("/booking-fail")
    public String bookingFalse() {
        return "user/booking_fail";
    }

    @GetMapping("/cancel-booking-success")
    public String cancelBookingSuccess() {
        return "user/cancel-booking-success";
    }

    @GetMapping("/my-account")
    public String myAccount(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = userService.user(userDetails.getUsername());

            if (user != null) {
                model.addAttribute("user", user);
            }
        }
        return "user/profile";
    }

    @ModelAttribute("userId")
    public Long getUserId(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = userService.user(userDetails.getUsername());

            if (user != null) {
                return user.getId();
            }
        }
        return null;
    }
}
