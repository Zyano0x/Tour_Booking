package com.project.tour_booking.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PatchExchange;

import com.project.tour_booking.Entity.TourImage;
import com.project.tour_booking.Service.TourImageService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tourimage")
public class TourImageController {
  private TourImageService tourImageService;

  @GetMapping("/tour/{tourId}")
  public ResponseEntity<TourImage> getTourImageByTourId(@PathVariable Long tourId) {
    return new ResponseEntity<>(tourImageService.getTourImageByTourId(tourId), HttpStatus.OK);
  }
}
