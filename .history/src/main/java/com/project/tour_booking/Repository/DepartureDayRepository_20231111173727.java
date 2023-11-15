package com.project.tour_booking.Repository;

import org.springframework.data.repository.CrudRepository;

import com.project.tour_booking.Entity.DepartureDay;

public interface DepartureDayRepository extends CrudRepository<DepartureDay, Long> {
  void deleteAllByTourId(Long tourId);
}
