package com.project.tour_booking.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Service.TourImageService;
import com.project.tour_booking.Service.TourService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/tour")
@AllArgsConstructor
public class TourController {
  private TourService tourService;
  private TourImageService tourImageService;

  @PostMapping("")
  public ResponseEntity<String> saveTour(@RequestBody Tour tour) {
    tourService.saveTour(tour);
    tourImageService.saveTourImageFromTour(tour.getImages(), tour.getId());
    return new ResponseEntity<>("THÊM TOUR THÀNH CÔNG!", HttpStatus.CREATED);
  }

  @GetMapping("/{tourId}")
  public ResponseEntity<Tour> getTour(@PathVariable Long tourId) {
    return new ResponseEntity<>(tourService.getTour(id), HttpStatus.OK);
  }
}
