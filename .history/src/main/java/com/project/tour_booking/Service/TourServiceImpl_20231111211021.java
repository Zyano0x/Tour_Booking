package com.project.tour_booking.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.DepartureDay;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Entity.TourImage;
import com.project.tour_booking.Exception.TourNotFoundException;
import com.project.tour_booking.Repository.DepartureDayRepository;
import com.project.tour_booking.Repository.TourImageRepository;
import com.project.tour_booking.Repository.TourRepository;
import com.project.tour_booking.Repository.TypeOfTourRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TourServiceImpl implements TourService {
  private TourRepository tourRepository;
  private TypeOfTourRepository typeOfTourRepository;
  private TourImageRepository tourImageRepository;
  private DepartureDayRepository departureDayRepository;

  @Override
  public void saveTour(Tour tour) {
    tourRepository.save(tour);
  }

  @Override
  public Tour getTour(Long id) {
    Optional<Tour> tour = tourRepository.findById(id);
    if (tour.isPresent()) {
      return tour.get();
    } else
      throw new TourNotFoundException(id);
  }

  @Override
  public List<Tour> getTours() {
    return (List<Tour>) tourRepository.findAll();
  }

  @Override
  public Tour updateTour(Tour tour, Long tourId) {
    Optional<Tour> updateTour = tourRepository.findById(tourId);
    if (updateTour.isPresent()) {
      Tour updateTour2 = updateTour.get();
      updateTour2.setName(tour.getName());
      updateTour2.setDescription(tour.getDescription());
      updateTour2.setTime(tour.getTime());
      updateTour2.setSchedule(tour.getSchedule());
      updateTour2.setQuantity(tour.getQuantity());
      updateTour2.setPriceForAdult(tour.getPriceForAdult());
      updateTour2.setPriceForChildren(tour.getPriceForChildren());
      updateTour2.setDepartureDays(tour.getDepartureDays());
      updateTour2.setDeparturePoint(tour.getDeparturePoint());
      updateTour2.setImages(tour.getImages());
      updateTour2.setTotId(tour.getTotId());

      updateTour2.setTypeOfTour(typeOfTourRepository.findById(tour.getTotId()).get());

      Tour tour2 = tourRepository.findById(tourId).get();
      // Cần tối ưu hơn về thuật toán
      List<LocalDate> departureDays = updateTour2.getDepartureDays();
      departureDayRepository.deleteAllByTourId(tourId);
      for (LocalDate departureDay : departureDays) {
        DepartureDay newDepartureDay = new DepartureDay(departureDay);
        newDepartureDay.setTour(tour2);
        departureDayRepository.save(newDepartureDay);
      }

      // Cần tối ưu hơn về thuật toán
      List<String> tourImageStrings = updateTour2.getImages();
      tourImageRepository.deleteAllByTourId(tourId);
      for (String path : tourImageStrings) {
        TourImage tourImage = new TourImage(path, tourId);
        tourImage.setTour(tour2);
        tourImageRepository.save(tourImage);
      }

      return tourRepository.save(updateTour2);
    } else
      throw new TourNotFoundException(tourId);
  }

  @Override
  public void deleteTour(Long tourId) {
    tourRepository.deleteById(tourId);
  }
}
