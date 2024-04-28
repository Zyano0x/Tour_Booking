package com.project.tour_booking.Service.TypeOfTour;

import java.util.List;

import com.project.tour_booking.DTO.TypeOfTourDTO;
import com.project.tour_booking.Entity.TypeOfTour;

public interface TypeOfTourService {
  TypeOfTour saveTOT(TypeOfTourDTO typeOfTourDTO);

    TypeOfTour getTOT(Long totId);

    List<TypeOfTour> getTOTS();

    TypeOfTour updateTOTStatus(Long totId);

    TypeOfTour updateTOT(TypeOfTour typeOfTour, Long totId);
}
