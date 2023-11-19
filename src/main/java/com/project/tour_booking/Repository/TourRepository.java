package com.project.tour_booking.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.tour_booking.Entity.Tour;

public interface TourRepository extends CrudRepository<Tour, Long> {
  List<Tour> findByTypeOfTourId(Long totId);
}
