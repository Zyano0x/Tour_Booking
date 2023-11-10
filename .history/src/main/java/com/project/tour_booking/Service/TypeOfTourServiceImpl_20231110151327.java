package com.project.tour_booking.Service;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.TypeOfTour;
import com.project.tour_booking.Repository.TypeOfTourRepository;
import com.project.tour_booking.exception.TypeOfTourNotFoundException;

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
    Optional<TypeOfTour> tot = typeOfTourRepository.findById(id);
    if (tot.isPresent())
      return tot.get();
    else
      throw new TypeOfTourNotFoundException(id);
  }

  @Override
  public List<TypeOfTour> getTOTS() {
    return (List<TypeOfTour>) typeOfTourRepository.findAll();
  }

  @Override
  public TypeOfTour updateTOT(TypeOfTour typeOfTour, Long id) {
    TypeOfTour updateTOT = typeOfTourRepository.findById(id).get();
    updateTOT.setName(typeOfTour.getName());
    updateTOT.setDescription(typeOfTour.getDescription());
    return typeOfTourRepository.save(updateTOT);
  }

  @Override
  public void deleteTOT(Long id) {
    // TODO Auto-generated method stub

  }
}
