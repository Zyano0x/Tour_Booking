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

import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Service.Booking.BookingService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BookingController {
  private BookingService bookingService;

  @PostMapping("/booking/tour/{tourId}")
  public ResponseEntity<String> saveBooking(@Valid @RequestBody Booking booking,
      @PathVariable Long tourId) {
    bookingService.saveBooking(booking, userId, tourId);
    return new ResponseEntity<>("ĐẶT TOUR THÀNH CÔNG!", HttpStatus.CREATED);
  }

  @GetMapping("/booking/{bookingId}")
  public ResponseEntity<Booking> getBooking(@PathVariable Long bookingId) {
    return new ResponseEntity<>(bookingService.getBooking(bookingId), HttpStatus.OK);
  }

  @GetMapping("/admin/booking/user/{userId}/tour/{tourId}")
  public ResponseEntity<List<Booking>> getBookingByUserIdAndTourId(@PathVariable Long userId,
      @PathVariable Long tourId) {
    return new ResponseEntity<>(bookingService.getBookingByUserIdAndTourId(userId, tourId), HttpStatus.OK);
  }

  @GetMapping("/booking/user/{userId}")
  public ResponseEntity<List<Booking>> getAllBookingByUserId(@PathVariable Long userId) {
    return new ResponseEntity<>(bookingService.getAllBookingByUserId(userId), HttpStatus.OK);
  }

  @GetMapping("/booking/tour/{tourId}")
  public ResponseEntity<List<Booking>> getAllBookingByTourId(@PathVariable Long tourId) {
    return new ResponseEntity<>(bookingService.getAllBookingByTourId(tourId), HttpStatus.OK);
  }

  @PutMapping("/admin/update-status-booking/{bookingId}")
  public ResponseEntity<String> updateStatusBooking(@PathVariable Long bookingId) {
    bookingService.updateStatusBooking(bookingId);
    return new ResponseEntity<>("CẬP NHẬT TRẠNG THÁI THÀNH CÔNG!", HttpStatus.OK);
  }
}
