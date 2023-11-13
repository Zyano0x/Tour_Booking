package com.project.tour_booking.Service;

import java.time.LocalDate;
import java.util.List;

public interface DepartureDayService {
  void saveDepartureDayFromTour(List<LocalDate> departureDays, Long tourId);

}
