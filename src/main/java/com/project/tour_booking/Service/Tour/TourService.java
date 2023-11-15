package com.project.tour_booking.Service.Tour;

import java.util.List;

import com.project.tour_booking.Entity.Tour;

public interface TourService {
  void saveTour(Tour tour);

  Tour getTour(Long id);

  List<Tour> getTours();

  Tour updateTour(Tour tour, Long id);

  void deleteTour(Long id);
}
