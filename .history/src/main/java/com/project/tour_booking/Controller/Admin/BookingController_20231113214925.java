package com.project.tour_booking.Controller.Admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Service.Admin.Booking.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
  private BookingService bookingService;

  @PostMapping("/user/{userId}/tout/{tourId}")
  public ResponseEntity<String> saveBooking(@Valid @RequestBody Booking booking, @PathVariable Long userId,
      Long tourId) {
    bookingService.saveBooking(booking, userId, tourId);
    return new ResponseEntity<>("ĐẶT TOUR THÀNH CÔNG!", HttpStatus.CREATED);
  }
}
