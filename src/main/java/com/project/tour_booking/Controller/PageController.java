package com.project.tour_booking.Controller;

import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Service.User.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse res, Model m, HttpSession session) {
        String msg = null;

        Cookie[] cookies2 = request.getCookies();
        for (int i = 0; i < cookies2.length; i++) {
            if (cookies2[i].getName().equals("Authorization")) {
                cookies2[i].setMaxAge(0);
                res.addCookie(cookies2[i]);
                msg = "Logout successfully";
            }
        }
        session.setAttribute("msg", msg);
        return "redirect:/";
    }
}
