package com.project.tour_booking.Service.TourReview;

import java.util.List;

import com.project.tour_booking.Entity.TourReview;

public interface TourReviewService {
  void saveTourReview(TourReview tourReview, Long userId, Long tourId);

  TourReview getTourReview(Long tourReviewId);

  TourReview getTourReviewByTourIdAndUserId(Long tourId, Long userId);

  List<TourReview> getAllTourReviewByTourId(Long tourId);

  List<TourReview> getAllTourReviewByUserId(Long userId);

  void deleteTourReview(Long tourReviewId);
}