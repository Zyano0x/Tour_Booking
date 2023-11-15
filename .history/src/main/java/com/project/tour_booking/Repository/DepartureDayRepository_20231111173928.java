package com.project.tour_booking.Repository;

import org.springframework.data.repository.CrudRepository;

import com.project.tour_booking.Entity.DepartureDay;

import jakarta.transaction.Transactional;

public interface DepartureDayRepository extends CrudRepository<DepartureDay, Long> {
  @Transactional
  void deleteAllByTourId(Long tourId);
}
