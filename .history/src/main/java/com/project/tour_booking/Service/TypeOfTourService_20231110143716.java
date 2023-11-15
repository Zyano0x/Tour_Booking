package com.project.tour_booking.Service;

import java.util.Optional;

import com.project.tour_booking.Entity.TypeOfTour;

public interface TypeOfTourService {
  void saveTOT(TypeOfTour typeOfTour);

  Optional<TypeOfTour> getTOT(Long id);
}
