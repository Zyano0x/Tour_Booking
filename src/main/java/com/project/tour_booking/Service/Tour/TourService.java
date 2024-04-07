package com.project.tour_booking.Service.Tour;

import java.util.List;

import com.project.tour_booking.DTO.TourDTO;
import com.project.tour_booking.Entity.Tour;

public interface TourService {
  Tour saveTour(TourDTO tourDTO);

  Tour getTour(Long tourId);

  List<Tour> getTours();

  List<Tour> getTourByTypeOfTourId(Long totId);

  Tour updateTour(TourDTO tourDTO, Long tourId);

  void updateTourStatus(Long tourId);
}
