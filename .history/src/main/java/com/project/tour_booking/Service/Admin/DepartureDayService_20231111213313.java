package com.project.tour_booking.Service;

import java.time.LocalDate;
import java.util.List;

import com.project.tour_booking.Entity.DepartureDay;

public interface DepartureDayService {
  void saveDepartureDayFromTour(List<LocalDate> departureDays, Long tourId);

  void saveDepartureDay(DepartureDay departureDay);

  DepartureDay getDepartureDay(Long departureDayId);

  List<DepartureDay> getDepartureDaysByTourId(Long tourId);

  DepartureDay updateDepartureDay(DepartureDay departureDay, Long departureDayId);

  void deleteDepartureDay(Long departureDayId);
}
