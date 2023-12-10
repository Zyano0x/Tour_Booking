package com.project.tour_booking.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class PageController {
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
}
