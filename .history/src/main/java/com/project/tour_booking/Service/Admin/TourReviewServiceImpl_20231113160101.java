package com.project.tour_booking.Service.Admin;

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

  }

  @Override
  public TourReview getTourReview(Long tourReviewId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TourReview getTourReviewByTourIdAndUserId(Long tourId, Long userId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<TourReview> getAllTourReviewByTourId(Long tourId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<TourReview> getAllTourReviewByUserId(Long userId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TourReview updateTourReview(TourReview tourReview, Long tourReviewId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void deleteTourReview(Long tourReviewId) {
    // TODO Auto-generated method stub

  }
}
