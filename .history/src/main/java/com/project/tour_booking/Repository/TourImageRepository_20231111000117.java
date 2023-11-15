package com.project.tour_booking.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.tour_booking.Entity.TourImage;

import jakarta.transaction.Transactional;

public interface TourImageRepository extends CrudRepository<TourImage, Long> {
  @Transactional
  List<TourImage> findAllByTourId(Long tourId);
}
