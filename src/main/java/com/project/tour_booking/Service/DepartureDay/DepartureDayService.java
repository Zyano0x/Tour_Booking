package com.project.tour_booking.Service.DepartureDay;

import java.util.List;

import com.project.tour_booking.DTO.DepartureDayDTO;
import com.project.tour_booking.Entity.DepartureDay;

public interface DepartureDayService {
  DepartureDay saveDepartureDay(DepartureDayDTO departureDayDTO);

  DepartureDay getDepartureDay(Long departureDayId);

  List<DepartureDay> getDepartureDays();

  List<DepartureDay> getDepartureDaysByTourId(Long tourId);

  DepartureDay updateDepartureDay(DepartureDayDTO departureDayDTO, Long departureDayId);

  DepartureDay updateDepartureDayStatus(Long departureDayId);
}
