package com.project.tour_booking.Service.Booking;

import java.util.List;

import com.project.tour_booking.DTO.BookingDTO;
import com.project.tour_booking.Entity.Booking;

public interface BookingService {
  void saveBooking(BookingDTO bookingDTO);

  Booking getBooking(Long bookingId);

  List<Booking> getBookings();

  List<Booking> getAllBookingByUserId(Long userId);

  List<Booking> getBookingIsValidOfUserId(Long userId);

  List<Booking> getAllBookingByTourId(Long tourId);

  List<Booking> getBookingByUserIdAndTourId(Long userId, Long tourId);

  void updateBooking(BookingDTO bookingDTO, Long bookingId);

  void updateBookingStatus(Long bookingId);
}
