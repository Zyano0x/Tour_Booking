package com.project.tour_booking.Service.Admin.TourReview;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.TourReview;
import com.project.tour_booking.Repository.TourReviewRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TourReviewServiceImpl implements TourReviewService {
  private TourReviewRepository tourReviewRepository;

  @Override
  public void saveTourReview(TourReview tourReview) {
    tourReviewRepository.save(tourReview);
  }

  @Override
  public TourReview getTourReview(Long tourReviewId) {
    return tourReviewRepository.findById(tourReviewId).get();
  }

  @Override
  public TourReview getTourReviewByTourIdAndUserId(Long tourId, Long userId) {
    return tourReviewRepository.findByTourIdAndUserId(tourId, userId).get();
  }

  @Override
  public List<TourReview> getAllTourReviewByTourId(Long tourId) {
    return tourReviewRepository.findAllByTourId(tourId);
  }

  @Override
  public List<TourReview> getAllTourReviewByUserId(Long userId) {
    return tourReviewRepository.findAllByUserId(userId);
  }

  @Override
  public void deleteTourReview(Long tourReviewId) {

  }
}
