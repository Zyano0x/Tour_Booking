package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.TourReviewDTO;
import com.project.tour_booking.Entity.TourReview;
import com.project.tour_booking.Service.TourReview.TourReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TourReviewController {
    private TourReviewService tourReviewService;

    @PostMapping("/tour-review")
    public ResponseEntity<String> saveTourReview(@Valid @RequestBody TourReviewDTO tourReviewDTO) {
        tourReviewService.saveTourReview(tourReviewDTO);
        return new ResponseEntity<>("THÊM BÌNH LUẬN THÀNH CÔNG!", HttpStatus.CREATED);
    }

    @GetMapping("/admin/tour-review/{tourReviewId}")
    public ResponseEntity<TourReview> getTourReview(@PathVariable Long tourReviewId) {
        return new ResponseEntity<>(tourReviewService.getTourReview(tourReviewId), HttpStatus.OK);
    }

    @GetMapping("/admin/user/{userId}/tour/{tourId}/tour-review")
    public ResponseEntity<TourReview> getTourReviewByTourIdAndUserId(@PathVariable Long userId, @PathVariable Long tourId) {
        return new ResponseEntity<>(tourReviewService.getTourReviewByTourIdAndUserId(tourId, userId), HttpStatus.OK);
    }

    @GetMapping("/admin/user/{userId}/tour-reviews")
    public ResponseEntity<List<TourReview>> getAllTourReviewByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(tourReviewService.getAllTourReviewByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/tour/{tourId}/tour-reviews")
    public ResponseEntity<List<TourReview>> getAllTourReviewByTourId(@PathVariable Long tourId) {
        return new ResponseEntity<>(tourReviewService.getAllTourReviewByTourId(tourId), HttpStatus.OK);
    }

    @PutMapping("/update-tour-review/{tourReviewId}")
    public ResponseEntity<TourReview> updateTourReview(@Valid @RequestBody TourReview tourReview, @PathVariable Long tourReviewId) {
        return new ResponseEntity<>(tourReviewService.updateTourReview(tourReview, tourReviewId), HttpStatus.OK);
    }

    @DeleteMapping("/delete-tour-review/{tourReviewId}")
    public ResponseEntity<String> deleteTourReview(@PathVariable Long tourReviewId) {
        tourReviewService.deleteTourReview(tourReviewId);
        return new ResponseEntity<>("XÓA BÌNH LUẬN THÀNH CÔNG", HttpStatus.OK);
    }
}
