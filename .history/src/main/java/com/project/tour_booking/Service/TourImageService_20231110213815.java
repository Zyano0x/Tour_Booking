package com.project.tour_booking.Service;

import java.util.List;

import com.project.tour_booking.Entity.TourImage;

public interface TourImageService {
  void saveTourImageFromTour(List<String> tourImages, Long tourId);

  void saveTourImage(TourImage tourImage);

  List<TourImage> getTourImageByTourId(Long tourId);

  TourImage updateTourImage(TourImage tourImage, Long id);

  void deleteTourImage(Long id);
}
