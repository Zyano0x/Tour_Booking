package com.project.tour_booking.Service.TourReview;

import java.util.List;

import com.project.tour_booking.DTO.TourReviewDTO;
import com.project.tour_booking.Entity.TourReview;

public interface TourReviewService {
  void saveTourReview(TourReviewDTO tourReviewDTO);

  TourReview getTourReview(Long tourReviewId);

  TourReview getTourReviewByTourIdAndUserId(Long tourId, Long userId);

  List<TourReview> getAllTourReviewByTourId(Long tourId);

  // String getRatingByTourId(Long tourId);

  List<TourReview> getAllTourReviewByUserId(Long userId);

  TourReview updateTourReview(TourReviewDTO tourReviewDTO, Long tourReviewId);

  void deleteTourReview(Long tourReviewId);
}
