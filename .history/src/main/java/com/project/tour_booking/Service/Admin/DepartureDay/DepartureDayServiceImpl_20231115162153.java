package com.project.tour_booking.Service.Admin.DepartureDay;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.DepartureDay;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Exception.DepartureDayNotFoundException;
import com.project.tour_booking.Exception.TourNotFoundException;
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
    Optional<Tour> tourOptional = tourRepository.findById(departureDay.getTourIdForCrud());
    if (tourOptional.isPresent())
      newDepartureDay.setTour(tourOptional.get());
    else
      throw new TourNotFoundException(departureDay.getTourIdForCrud());
    departureDayRepository.save(newDepartureDay);
  }

  @Override
  public DepartureDay getDepartureDay(Long departureDayId) {
    Optional<DepartureDay> departureDayOptional = departureDayRepository.findById(departureDayId);
    if (departureDayOptional.isPresent())
      return departureDayOptional.get();
    else
      throw new DepartureDayNotFoundException(departureDayId);
  }

  @Override
  public List<DepartureDay> getDepartureDaysByTourId(Long tourId) {
    return departureDayRepository.findAllByTourId(tourId);
  }

  @Override
  public DepartureDay updateDepartureDay(DepartureDay departureDay, Long departureDayId) {
    Optional<DepartureDay> departureDayOptional = departureDayRepository.findById(departureDayId);
    if (departureDayOptional.isPresent()) {
      DepartureDay updateDepartureDay = departureDayOptional.get();
      updateDepartureDay.setDepartureDay(departureDay.getDepartureDay());
      Optional<Tour> tourOptional = tourRepository.findById(departureDay.getTourIdForCrud());
      if (tourOptional.isPresent())
        newDepartureDay.setTour(tourOptional.get());
      else
        throw new TourNotFoundException(departureDay.getTourIdForCrud());
      return departureDayRepository.save(updateDepartureDay);
    } else
      throw new DepartureDayNotFoundException(departureDayId);
  }

  @Override
  public void deleteDepartureDay(Long departureDayId) {
    Optional<DepartureDay> departureDayOptional = departureDayRepository.findById(departureDayId);
    if (departureDayOptional.isPresent())
      departureDayRepository.deleteById(departureDayId);
    else
      throw new DepartureDayNotFoundException(departureDayId);
  }
}
