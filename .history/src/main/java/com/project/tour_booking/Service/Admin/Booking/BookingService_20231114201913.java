package com.project.tour_booking.Service.Admin.Booking;

import java.util.List;

import com.project.tour_booking.Entity.Booking;

public interface BookingService {
  // void saveBooking(Booking booking, Long userId, Long tourId);

  Booking getBooking(Long bookingId);

  List<Booking> getBookingByUserIdAndTourId(Long userId, Long tourId);

  List<Booking> getAllBookingByTourId(Long tourId);

  List<Booking> getAllBookingByUserId(Long userId);

  void updateStatusBooking(Long bookingId);

  // void deleteBooking(Long bookingId);
}
