package com.project.tour_booking.Service;

import java.util.List;

import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Entity.TypeOfTour;

public interface TourService {
  void saveTour(Tour tour);

  Tour getTOT(Long id);

  List<Tour> getTOTS();

  Tour updateTOT(Tour tour, Long id);

  void deleteTOT(Long id);
}
