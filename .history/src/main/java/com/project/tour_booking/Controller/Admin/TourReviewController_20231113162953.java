package com.project.tour_booking.Controller.Admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.Service.Admin.TourReview.TourReviewService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/tour-review")
@AllArgsConstructor
public class TourReviewController {
  private TourReviewService tourReviewService;

  @PostMapping("/user/{userId}/tour/{tourId}")
}
