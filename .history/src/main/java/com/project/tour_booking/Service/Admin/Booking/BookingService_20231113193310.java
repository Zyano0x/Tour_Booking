package com.project.tour_booking.Service.Admin.Booking;

import com.project.tour_booking.Entity.Booking;

public interface BookingService {
  void saveBooking(Booking booking, Long userId, Long tourId);
}
