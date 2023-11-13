package com.project.tour_booking.Service;

import java.time.LocalDate;
import java.util.List;

import com.project.tour_booking.Entity.DepartureDay;

public interface DepartureDayService {
  void saveDepartureDayFromTour(List<LocalDate> departureDays, Long tourId);

  void saveDepartureDay(DepartureDay departureDay);
}
