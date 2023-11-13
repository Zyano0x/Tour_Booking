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

import com.project.tour_booking.Entity.DepartureDay;
import com.project.tour_booking.Service.DepartureDayService;
import com.project.tour_booking.Service.TourService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/departure-day")
public class DepartureDayController {
  private DepartureDayService departureDayService;
  private TourService tourService;

  @PostMapping("")
  public ResponseEntity<String> getDepartureDay(@RequestBody DepartureDay departureDay) {
    departureDay.setTour(tourService.getTour(departureDay.getTourIdForCrud()));
    departureDayService.saveDepartureDay(departureDay);
    return new ResponseEntity<>("THÊM NGÀY KHỞI HÀNH THÀNH CÔNG!", HttpStatus.OK);
  }

  @GetMapping("/{departureDayId")
  public ResponseEntity<DepartureDay> getDepartureDay(@PathVariable Long departureDayId) {
    return new ResponseEntity<>(departureDayService.getDepartureDay(departureDayId), HttpStatus.OK);
  }

  @GetMapping("/{tourId")
  public ResponseEntity<List<DepartureDay>> getDepartureDaysByTourId(@PathVariable Long tourId) {
    return new ResponseEntity<>(departureDayService.getDepartureDaysByTourId(tourId), HttpStatus.OK);
  }
}
