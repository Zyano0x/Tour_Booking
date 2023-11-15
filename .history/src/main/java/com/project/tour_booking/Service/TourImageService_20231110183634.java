package com.project.tour_booking.Service;

import java.util.List;

import com.project.tour_booking.Entity.TourImage;

public interface TourImageService {
  void saveTourImage(List<String> tourImages, Long tourId);

  TourImage gTourImage(Long tourId);
}
