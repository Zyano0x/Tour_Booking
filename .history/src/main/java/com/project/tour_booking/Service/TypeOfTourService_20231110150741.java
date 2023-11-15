package com.project.tour_booking.Service;

import java.util.List;

import com.project.tour_booking.Entity.TypeOfTour;

public interface TypeOfTourService {
  void saveTOT(TypeOfTour typeOfTour);

  TypeOfTour getTOT(Long id);

  List<TypeOfTour> getTOTS();

  TypeOfTour updateTOT(TypeOfTour typeOfTour, Long id);

  void deleteTOT(Long id);
}
