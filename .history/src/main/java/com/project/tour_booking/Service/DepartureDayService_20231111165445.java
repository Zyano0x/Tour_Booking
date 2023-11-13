package com.project.tour_booking.Service;

import java.util.List;

import com.project.tour_booking.Entity.DepartureDay;

public interface DepartureDayService {
  void saveDepartureDayFromTour(List<Long> departureDays, Long tourId);
}
