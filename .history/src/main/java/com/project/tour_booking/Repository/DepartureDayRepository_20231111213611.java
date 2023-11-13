package com.project.tour_booking.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.tour_booking.Entity.DepartureDay;

import jakarta.transaction.Transactional;

public interface DepartureDayRepository extends CrudRepository<DepartureDay, Long> {
  List<DepartureDay> findAllByTourId(Long tourId);

  @Transactional
  void deleteAllByTourId(Long tourId);
}
