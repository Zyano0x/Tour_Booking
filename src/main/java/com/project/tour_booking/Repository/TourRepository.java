package com.project.tour_booking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tour_booking.Entity.Tour;

public interface TourRepository extends JpaRepository<Tour, Long> {
  List<Tour> findAllByTypeOfTourId(Long totId);

  List<Tour> findAllByDestinationId(Long destinationId);
}
