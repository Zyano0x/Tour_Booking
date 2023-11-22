package com.project.tour_booking.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.DTO.TourDTO;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Service.Tour.TourService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TourController {
  private TourService tourService;

  @PostMapping("admin/tour")
  public ResponseEntity<String> saveTour(@Valid @RequestBody TourDTO tourDTO) {
    tourService.saveTour(tourDTO);
    return new ResponseEntity<>("THÊM TOUR THÀNH CÔNG!", HttpStatus.CREATED);
  }

  @GetMapping("/tour/{tourId}")
  public ResponseEntity<Tour> getTour(@PathVariable Long tourId) {
    return new ResponseEntity<>(tourService.getTour(tourId), HttpStatus.OK);
  }

  @GetMapping("/tour/all")
  public ResponseEntity<List<Tour>> getTours() {
    return new ResponseEntity<>(tourService.getTours(), HttpStatus.OK);
  }

  @GetMapping("/tour/type-of-tour/{typeOfTourId}")
  public ResponseEntity<List<Tour>> getTourByTypeOfTourId(@PathVariable Long typeOfTourId) {
    return new ResponseEntity<>(tourService.getTourByTypeOfTourId(typeOfTourId), HttpStatus.OK);
  }

  @PutMapping("/admin/update-tour/{tourId}")
  public ResponseEntity<Tour> updateTour(@Valid @RequestBody TourDTO tourDTO, @PathVariable Long tourId) {
    return new ResponseEntity<>(tourService.updateTour(tourDTO, tourId), HttpStatus.OK);
  }

  @PutMapping("/admin/update-tour-status/{tourId}")
  public ResponseEntity<String> updateTourStatus(@PathVariable Long tourId) {
    tourService.updateTourStatus(tourId);
    return new ResponseEntity<>("CHUYỂN ĐỔI TRẠNG THÁI THÀNH CÔNG!", HttpStatus.OK);
  }
}