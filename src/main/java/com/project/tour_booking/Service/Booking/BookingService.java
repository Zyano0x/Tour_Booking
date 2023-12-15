package com.project.tour_booking.Service.Booking;

import com.project.tour_booking.DTO.BookingDTO;
import com.project.tour_booking.Entity.Booking;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
  void saveBooking(BookingDTO bookingDTO);

  Booking getBooking(Long bookingId);

  List<Booking> getBookings();

  List<Booking> getAllBookingByUserId(Long userId);

  List<Booking> getBookingIsValidOfUserId(Long userId);

  List<Booking> getAllBookingByTourId(Long tourId);

  List<Booking> getBookingByUserIdAndTourId(Long userId, Long tourId);

  void updateBooking(BookingDTO bookingDTO, Long bookingId);

  ResponseEntity<?> updateBookingStatus(Long bookingId);

  ResponseEntity<?> confirmCancel(Long transactionCode, String confirmationToken);
}
