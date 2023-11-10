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

  @Override
  public Tour updateTour(Tour tour, Long id) {
    Optional<Tour> updateTour = tourRepository.findById(id);
    if (updateTour.isPresent()) {
      Tour updateTour2 = updateTour.get();
      updateTour2.setName(tour.getName());
      updateTour2.setContent(tour.getContent());
      updateTour2.setPrice(tour.getPrice());
      updateTour2.setDepartureDay(tour.getDepartureDay());
      updateTour2.setDeparturePoint(tour.getDeparturePoint());
      updateTour2.setImages(tour.getImages());
    }
    return null;
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
