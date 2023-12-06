package com.project.tour_booking.Controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Service.Tour.TourService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class PageController {
    private TourService tourService;

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

    // @GetMapping("tours")
    // public String tours(Model model, @RequestParam(name = "page", defaultValue =
    // "1") Integer pageNum) {
    // Page<Tour> tours = tourService.getToursForPagination(pageNum);
    // model.addAttribute("tours", tours);
    // model.addAttribute("totalPage", tours.getTotalPages());
    //
    // model.addAttribute("currentPage", pageNum);
    // return "user/tours";
    //
}
