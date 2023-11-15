package com.project.tour_booking.Service;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.Tour;
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
}
