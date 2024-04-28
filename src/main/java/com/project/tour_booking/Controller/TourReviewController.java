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
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TourReviewController {
    private TourReviewService tourReviewService;

    @PostMapping("/tour-reviews")
    public ResponseEntity<TourReview> saveTourReview(@Valid @RequestBody TourReviewDTO tourReviewDTO) {
        return new ResponseEntity<>(tourReviewService.saveTourReview(tourReviewDTO), HttpStatus.CREATED);
    }

    @GetMapping("/tour-reviews/tours/{tourId}")
    public ResponseEntity<List<TourReview>> getAllTourReviewByTourId(@PathVariable Long tourId) {
        return new ResponseEntity<>(tourReviewService.getAllTourReviewByTourId(tourId), HttpStatus.OK);
    }

    @PutMapping("/update-tour-reviews/{tourReviewId}")
    public ResponseEntity<TourReview> updateTourReview(@Valid @RequestBody TourReviewDTO tourReviewDTO,
            @PathVariable Long tourReviewId) {
        return new ResponseEntity<>(tourReviewService.updateTourReview(tourReviewDTO, tourReviewId), HttpStatus.OK);
    }

    @DeleteMapping("/delete-tour-reviews/{tourReviewId}")
    public ResponseEntity<String> deleteTourReview(@PathVariable Long tourReviewId) {
        tourReviewService.deleteTourReview(tourReviewId);
        return new ResponseEntity<>(" ", HttpStatus.NO_CONTENT);
    }
}
