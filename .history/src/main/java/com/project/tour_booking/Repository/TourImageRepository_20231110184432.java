package com.project.tour_booking.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.tour_booking.Entity.TourImage;

public interface TourImageRepository extends CrudRepository<TourImage, Long> {
  List<TourImage> findByTourId(Long tourId);
}
