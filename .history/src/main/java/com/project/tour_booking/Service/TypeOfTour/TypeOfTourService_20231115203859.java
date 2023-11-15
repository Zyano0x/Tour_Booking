package com.project.tour_booking.Service.TypeOfTour;

import java.util.List;

import com.project.tour_booking.Entity.TypeOfTour;

public interface TypeOfTourService {
  void saveTOT(TypeOfTour typeOfTour);

  TypeOfTour getTOT(Long totId);

  List<TypeOfTour> getTOTS();

  TypeOfTour updateTOT(TypeOfTour typeOfTour, Long totId);

  void deleteTOT(Long totId);
}
