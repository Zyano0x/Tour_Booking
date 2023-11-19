package com.project.tour_booking.Service.Tour;

import java.util.List;

import com.project.tour_booking.DTO.TourDTO;
import com.project.tour_booking.Entity.Tour;

public interface TourService {
  // void saveTour(Tour tour);

  void saveTour(TourDTO tourDTO);

  Tour getTour(Long tourId);

  List<Tour> getTours();

  Tour updateTour(TourDTO tourDTO, Long tourId);

  void updateTourStatus(Long tourId);
}
