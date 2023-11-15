package com.project.tour_booking.Controller.Admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

  @PostMapping("/user/{userId}/tour/{tourId}")
  public ResponseEntity<String> saveBooking(@Valid @RequestBody Booking booking, @PathVariable Long userId,
      Long tourId) {
    bookingService.saveBooking(booking, userId, tourId);
    return new ResponseEntity<>("ĐẶT TOUR THÀNH CÔNG!", HttpStatus.CREATED);
  }

  @GetMapping("/{bookingId}")
  public ResponseEntity<Booking> getBooking(@PathVariable Long bookingId) {
    return new ResponseEntity<>(bookingService.getBooking(bookingId), HttpStatus.OK);
  }

  @GetMapping("/user/{userId}/tour/{tourId}")
  public ResponseEntity<List<Booking>> getBookingByUserIdAndTourId(@PathVariable Long userId,
      @PathVariable Long tourId) {
    return new ResponseEntity<>(bookingService.getBookingByUserIdAndTourId(userId, tourId), HttpStatus.OK);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Booking>> getAllBookingByUserId(@PathVariable Long userId) {
    return new ResponseEntity<>(bookingService.getAllBookingByUserId(userId), HttpStatus.OK);
  }

  @GetMapping("/tour/{tourId}")
  public ResponseEntity<List<Booking>> getAllBookingByTourId(@PathVariable Long tourId) {
    return new ResponseEntity<>(bookingService.getAllBookingByTourId(tourId), HttpStatus.OK);
  }

  @DeleteMapping("/{bookingId}")
  public ResponseEntity<String> deleteBooking(@PathVariable Long bookingId) {
    return new ResponseEntity<>("XÓA ĐON ĐẶT TOUR THÀNH CÔNG!", HttpStatus.OK);
  }
}
