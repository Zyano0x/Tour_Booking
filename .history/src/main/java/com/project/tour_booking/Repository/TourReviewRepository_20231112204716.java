package com.project.tour_booking.Repository;

import org.springframework.data.repository.CrudRepository;

import com.project.tour_booking.Entity.TourReview;

public interface TourReviewRepository extends CrudRepository<TourReview, Long> {
  TourReview findByTourIdAndUserId(Long tourId, Long userid);
}
