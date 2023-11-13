package com.project.tour_booking.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.Entity.DepartureDay;
import com.project.tour_booking.Service.DepartureDayService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/departure-day")
public class DepartureDayController {
  private DepartureDayService departureDayService;

  @PostMapping("")
  public ResponseEntity<String> getDepartureDay(Long departureDayId) {
    return new ResponseEntity<>("THÊM NGÀY KHỞI HÀNH THÀNH CÔNG!", HttpStatus.OK);
  }
}
