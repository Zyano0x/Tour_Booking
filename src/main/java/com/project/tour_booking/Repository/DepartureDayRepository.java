package com.project.tour_booking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tour_booking.Entity.DepartureDay;

import jakarta.transaction.Transactional;

public interface DepartureDayRepository extends JpaRepository<DepartureDay, Long> {
  List<DepartureDay> findAllByTourId(Long tourId);

  @Transactional
  void deleteAllByTourId(Long tourId);
}
