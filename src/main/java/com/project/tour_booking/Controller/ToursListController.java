package com.project.tour_booking.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ToursListController {
    @GetMapping("tours_list")
    public String tourList() {
        return "user/tours_list";
    }
}
