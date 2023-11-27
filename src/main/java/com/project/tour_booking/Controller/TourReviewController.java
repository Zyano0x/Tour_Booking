package com.project.tour_booking.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.DTO.TourReviewDTO;
import com.project.tour_booking.Entity.TourReview;
import com.project.tour_booking.Service.TourReview.TourReviewService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TourReviewController {
  private TourReviewService tourReviewService;

  @PostMapping("/tour-review")
  public ResponseEntity<String> saveTourReview(@Valid @RequestBody TourReviewDTO tourReviewDTO) {
    tourReviewService.saveTourReview(tourReviewDTO);
    return new ResponseEntity<>("THÊM BÌNH LUẬN THÀNH CÔNG!",
        HttpStatus.CREATED);
  }

  @GetMapping("/admin/tour-review/{tourReviewId}")
  public ResponseEntity<TourReview> getTourReview(@PathVariable Long tourReviewId) {
    return new ResponseEntity<>(tourReviewService.getTourReview(tourReviewId), HttpStatus.OK);
  }

  @GetMapping("/admin/tour-review/user/{userId}/tour/{tourId}")
  public ResponseEntity<TourReview> getTourReviewByTourIdAndUserId(@PathVariable Long userId,
      @PathVariable Long tourId) {
    return new ResponseEntity<>(tourReviewService.getTourReviewByTourIdAndUserId(tourId, userId), HttpStatus.OK);
  }

  @GetMapping("/admin/tour-review/user/{userId}")
  public ResponseEntity<List<TourReview>> getAllTourReviewByUserId(@PathVariable Long userId) {
    return new ResponseEntity<>(tourReviewService.getAllTourReviewByUserId(userId), HttpStatus.OK);
  }

  @GetMapping("/tour-review/tour/{tourId}")
  public ResponseEntity<List<TourReview>> getAllTourReviewByTourId(@PathVariable Long tourId) {
    return new ResponseEntity<>(tourReviewService.getAllTourReviewByTourId(tourId), HttpStatus.OK);
  }

  @PutMapping("/update-tour-review/{tourReviewId}")
  public ResponseEntity<TourReview> updateTourReview(@Valid @RequestBody TourReview tourReview,
      @PathVariable Long tourReviewId) {
    return new ResponseEntity<>(tourReviewService.updateTourReview(tourReview, tourReviewId), HttpStatus.OK);
  }

  @DeleteMapping("/delete-tour-review/{tourReviewId}")
  public ResponseEntity<String> deleteTourReview(@PathVariable Long tourReviewId) {
    tourReviewService.deleteTourReview(tourReviewId);
    return new ResponseEntity<>("XÓA BÌNH LUẬN THÀNH CÔNG", HttpStatus.OK);
  }
}