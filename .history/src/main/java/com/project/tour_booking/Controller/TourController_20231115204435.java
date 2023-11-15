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

import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Service.DepartureDay.DepartureDayService;
import com.project.tour_booking.Service.Tour.TourService;
import com.project.tour_booking.Service.TourImage.TourImageService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TourController {
  private TourService tourService;
  private TourImageService tourImageService;
  private DepartureDayService departureDayService;

  @PostMapping("admin/tour")
  public ResponseEntity<String> saveTour(@Valid @RequestBody Tour tour) {
    tourService.saveTour(tour);
    departureDayService.saveDepartureDayFromTour(tour.getDepartureDays(), tour.getId());
    tourImageService.saveTourImageFromTour(tour.getImages(), tour.getId());
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

  @PutMapping("/admin/update-tour/{tourId}")
  public ResponseEntity<Tour> updateTour(@Valid @RequestBody Tour tour, @PathVariable Long tourId) {
    return new ResponseEntity<>(tourService.updateTour(tour, tourId), HttpStatus.OK);
  }

  @DeleteMapping("/admin/delete-tour/{tourId}")
  public ResponseEntity<String> deleteTour(@PathVariable Long tourId) {
    tourService.deleteTour(tourId);
    return new ResponseEntity<>("XÓA TOUR THÀNH CÔNG!", HttpStatus.OK);
  }
}
