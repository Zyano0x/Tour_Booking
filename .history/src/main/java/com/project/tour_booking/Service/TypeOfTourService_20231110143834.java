package com.project.tour_booking.Service;

import com.project.tour_booking.Entity.TypeOfTour;

public interface TypeOfTourService {
  void saveTOT(TypeOfTour typeOfTour);

  TypeOfTour getTOT(Long id);
}
