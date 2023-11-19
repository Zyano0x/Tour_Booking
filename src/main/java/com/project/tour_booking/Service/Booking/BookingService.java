package com.project.tour_booking.Service.Booking;

import java.util.List;

import com.project.tour_booking.DTO.BookingDTO;
import com.project.tour_booking.Entity.Booking;

public interface BookingService {
  void saveBooking(BookingDTO bookingDTO);

  Booking getBooking(Long bookingId);

  // List<Booking> getBookingByUserIdAndTourId(Long userId, Long tourId);

  // List<Booking> getAllBookingByTourId(Long tourId);

  List<Booking> getAllBookingByUserId(Long userId);

  void updateBooking(BookingDTO bookingDTO, Long bookingId);

  void updateStatusBooking(Long bookingId);

  // void deleteBooking(Long bookingId);
}
