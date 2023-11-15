package com.project.tour_booking.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Entity.TypeOfTour;
import com.project.tour_booking.Exception.TourNotFoundException;
import com.project.tour_booking.Exception.TypeOfTourNotFoundException;
import com.project.tour_booking.Repository.TourRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TourServiceImpl implements TourService {
  private TourRepository tourRepository;

  @Override
  public void saveTour(Tour tour) {
    tourRepository.save(tour);
  }

  @Override
  public Tour getTour(Long id) {
    Optional<Tour> tour = tourRepository.findById(id);
    if (tour.isPresent())
      return tour.get();
    else
      throw new TourNotFoundException(id);
  }

  @Override
  public List<Tour> getTourS() {
    return (List<Tour>) tourRepository.findAll();
  }

  // @Override
  // public TypeOfTour updateTOT(TypeOfTour typeOfTour, Long id) {
  // TypeOfTour updateTOT = typeOfTourRepository.findById(id).get();
  // updateTOT.setName(typeOfTour.getName());
  // updateTOT.setDescription(typeOfTour.getDescription());
  // return typeOfTourRepository.save(updateTOT);
  // }

  @Override
  public void deleteTour(Long id) {
    tourRepository.deleteById(id);
  }
}
