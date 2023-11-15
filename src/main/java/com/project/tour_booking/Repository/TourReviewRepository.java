package com.project.tour_booking.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.project.tour_booking.Entity.TourReview;

public interface TourReviewRepository extends CrudRepository<TourReview, Long> {
  Optional<TourReview> findByTourIdAndUserId(Long tourId, Long userid);

  List<TourReview> findAllByTourId(Long tourId);

  List<TourReview> findAllByUserId(Long userId);
}
