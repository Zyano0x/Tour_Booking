package com.project.tour_booking.Controller.Admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.Entity.TourReview;
import com.project.tour_booking.Service.Admin.TourReview.TourReviewService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/tour-review")
@AllArgsConstructor
public class TourReviewController {
  private TourReviewService tourReviewService;

  @PostMapping("/user/{userId}/tour/{tourId}")
  public ResponseEntity<String> saveTourReview(@RequestBody TourReview tourReview, @PathVariable Long userId,
      @PathVariable Long tourId) {
    return new ResponseEntity<>("THÊM BÌNH LUẬN THÀNH CÔNG!", HttpStatus.CREATED);
  }

  @GetMapping("/{tourReviewId}")
  public ResponseEntity<TourReview> getTourReview(@PathVariable Long tourReviewId) {
    return new ResponseEntity<>(tourReviewService.getTourReview(tourReviewId), HttpStatus.OK);
  }

  @GetMapping("/user/{userId}/tour/{tourId}")
  public ResponseEntity<TourReview> getTourReviewByTourIdAndUserId(@PathVariable Long userId,
      @PathVariable Long tourId) {
    return new ResponseEntity<>(tourReviewService.getTourReviewByTourIdAndUserId(tourId, userId), HttpStatus.OK);
  }
}
