package com.project.tour_booking.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.TypeOfTour;
import com.project.tour_booking.Exception.TypeOfTourNotFoundException;
import com.project.tour_booking.Repository.TypeOfTourRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TypeOfTourServiceImpl implements TypeOfTourService {
  private TypeOfTourRepository typeOfTourRepository;

  @Override
  public void saveTOT(TypeOfTour typeOfTour) {
    typeOfTourRepository.save(typeOfTour);
  }

  @Override
  public TypeOfTour getTOT(Long totId) {
    Optional<TypeOfTour> tot = typeOfTourRepository.findById(totId);
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
    Optional<TypeOfTour> updateTOT = typeOfTourRepository.findById(id);
    if (updateTOT.isPresent()) {
      TypeOfTour updateTOT2 = updateTOT.get();
      updateTOT2.setName(typeOfTour.getName());
      updateTOT2.setDescription(typeOfTour.getDescription());
      return typeOfTourRepository.save(updateTOT2);
    } else
      throw new TypeOfTourNotFoundException(id);
  }

  @Override
  public void deleteTOT(Long id) {
    typeOfTourRepository.deleteById(id);
  }
}
