package com.project.tour_booking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tour_booking.Entity.TourImage;

import jakarta.transaction.Transactional;

public interface TourImageRepository extends JpaRepository<TourImage, Long> {
  List<TourImage> findAllByTourId(Long tourId);

  @Transactional
  void deleteAllByTourId(Long tourId);
}
