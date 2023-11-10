package com.project.tour_booking.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.TypeOfTour;
import com.project.tour_booking.Repository.TypeOfTourRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TypeOfTourServiceImpl implements TypeOfTourService {
  TypeOfTourRepository typeOfTourRepository;

  @Override
  public void saveTOT(TypeOfTour typeOfTour) {
    typeOfTourRepository.save(typeOfTour);
  }

  @Override
  public TypeOfTour getTOT(Long id) {
    return typeOfTourRepository.findById(id).get();
  }
}
