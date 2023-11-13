package com.project.tour_booking.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.DepartureDay;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Repository.DepartureDayRepository;
import com.project.tour_booking.Repository.TourRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartureDayServiceImpl implements DepartureDayService {
  private DepartureDayRepository departureDayRepository;
  private TourRepository tourRepository;

  @Override
  public void saveDepartureDayFromTour(List<LocalDate> departureDays, Long tourId) {
    Tour tour = tourRepository.findById(tourId).get();
    for (LocalDate localDate : departureDays) {
      DepartureDay departureDay = new DepartureDay(localDate, tourId);
      departureDay.setTour(tour);
      departureDayRepository.save(departureDay);
    }
  }

  @Override
  public void saveDepartureDay(DepartureDay departureDay) {
    DepartureDay newDepartureDay = new DepartureDay(departureDay.getDepartureDay(), departureDay.getTourIdForCrud());
    newDepartureDay.setTour(tourRepository.findById(departureDay.getTourIdForCrud()).get());
  }

  @Override
  public DepartureDay getDepartureDay(Long departureDayId) {
    return departureDayRepository.findById(departureDayId).get();
  }

  @Override
  public List<DepartureDay> getDepartureDaysByTourId(Long tourId) {
    return departureDayRepository.findAllByTourId(tourId);
  }
}
