package com.project.tour_booking.Service;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Repository.TourReviewRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TourReviewServiceImpl {
  private TourReviewRepository tourReviewRepository;

}
