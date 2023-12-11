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

import com.project.tour_booking.DTO.BookingDTO;
import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Service.Booking.BookingService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BookingController {
  private BookingService bookingService;

  @PostMapping("/booking")
  public ResponseEntity<String> saveBooking(@Valid @RequestBody BookingDTO bookingDTO) {
    bookingService.saveBooking(bookingDTO);
    return new ResponseEntity<>("ĐẶT TOUR THÀNH CÔNG!", HttpStatus.CREATED);
  }

  @GetMapping("/booking/{bookingId}")
  public ResponseEntity<Booking> getBooking(@PathVariable Long bookingId) {
    return new ResponseEntity<>(bookingService.getBooking(bookingId), HttpStatus.OK);
  }

  @GetMapping("/bookings")
  public ResponseEntity<List<Booking>> getBookings() {
    return new ResponseEntity<>(bookingService.getBookings(), HttpStatus.OK);
  }

  @GetMapping("/user/{userId}/bookings")
  public ResponseEntity<List<Booking>> getAllBookingByUserId(@PathVariable Long userId) {
    return new ResponseEntity<>(bookingService.getAllBookingByUserId(userId), HttpStatus.OK);
  }

  @GetMapping("/user/{userId}/bookings-valid")
  public ResponseEntity<List<Booking>> getBookingIsValidOfUserId(@PathVariable Long userId) {
    return new ResponseEntity<>(bookingService.getBookingIsValidOfUserId(userId), HttpStatus.OK);
  }

  @GetMapping("/admin/tour/{tourId}/bookings")
  public ResponseEntity<List<Booking>> getAllBookingByTourId(@PathVariable Long tourId) {
    return new ResponseEntity<>(bookingService.getAllBookingByTourId(tourId),
        HttpStatus.OK);
  }

  @GetMapping("/admin/user/{userId}/tour/{tourId}/bookings")
  public ResponseEntity<List<Booking>> getBookingByUserIdAndTourId(@PathVariable Long userId,
      @PathVariable Long tourId) {
    return new ResponseEntity<>(bookingService.getBookingByUserIdAndTourId(userId, tourId),
        HttpStatus.OK);
  }

  @PutMapping("/admin/update-booking-status/{bookingId}")
  public ResponseEntity<String> updateBookingStatus(@PathVariable Long bookingId) {
    bookingService.updateBookingStatus(bookingId);
    return new ResponseEntity<>("CẬP NHẬT TRẠNG THÁI THÀNH CÔNG!", HttpStatus.OK);
  }

  @PutMapping("/update-booking/{bookingId}")
  public ResponseEntity<String> updateBooking(@Valid @RequestBody BookingDTO bookingDTO, @PathVariable Long bookingId) {
    bookingService.updateBooking(bookingDTO, bookingId);
    return new ResponseEntity<>("CẬP NHẬT ĐƠN ĐẶT TOUR THÀNH CÔNG!", HttpStatus.OK);
  }
}
