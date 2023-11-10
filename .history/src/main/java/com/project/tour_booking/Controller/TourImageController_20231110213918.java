package com.project.tour_booking.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.Entity.TourImage;
import com.project.tour_booking.Service.TourImageService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tourimage")
public class TourImageController {
  private TourImageService tourImageService;

  @PostMapping("")
  public ResponseEntity<String> saveTourImage(@RequestBody TourImage tourImage) {
    tourImageService.saveTourImage(tourImage);
    return new ResponseEntity<>("THÊM ẢNH TOUR THÀNH CÔNG!", HttpStatus.CREATED);
  }

  @GetMapping("/tour/{tourId}")
  public ResponseEntity<List<TourImage>> getTourImageByTourId(@PathVariable Long tourId) {
    return new ResponseEntity<>(tourImageService.getTourImageByTourId(tourId), HttpStatus.OK);
  }
}
