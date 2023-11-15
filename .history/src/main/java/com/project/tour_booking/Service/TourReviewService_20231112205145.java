package com.project.tour_booking.Service;

import java.util.List;

import com.project.tour_booking.Entity.TourReview;

public interface TourReviewService {
  void saveTourReview(TourReview tourReview);

  TourReview gTourReview(Long tourReviewId);

  TourReview getTourReviewByTourIdAndUserId(Long tourId, Long userId);

  List<TourReview> getAllTourReviewByTourId(Long tourId);

  List<TourReview> getAllTourReviewByUserId(Long userId);

  TourReview updateTourReview(TourReview tourReview, Long tourReviewId);

  void deleteTourReview(Long tourReviewId);
}
