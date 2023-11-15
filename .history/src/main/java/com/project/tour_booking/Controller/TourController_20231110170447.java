package com.project.tour_booking.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Service.TourService;

@RestController
@RequestMapping("/api/tour")
public class TourController {
  private TourService tourService;

  @PostMapping("")
  public ResponseEntity<String> saveTour(@RequestBody Tour tour) {
    tourService.saveTour(tour);
    return new ResponseEntity<>("THÊM TOUR THÀNH CÔNG!", HttpStatus.CREATED);
  }
}
