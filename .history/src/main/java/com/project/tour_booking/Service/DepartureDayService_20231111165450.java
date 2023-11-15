package com.project.tour_booking.Service;

import java.util.List;

public interface DepartureDayService {
  void saveDepartureDayFromTour(List<Long> departureDays, Long tourId);
}
