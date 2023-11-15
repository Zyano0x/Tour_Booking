package com.project.tour_booking.Service.TourImage;

import java.util.List;

import com.project.tour_booking.Entity.TourImage;

public interface TourImageService {
  void saveTourImageFromTour(List<String> tourImages, Long tourId);

  void saveTourImage(TourImage tourImage);

  TourImage getTourImage(Long tourImageId);

  List<TourImage> getTourImageByTourId(Long tourId);

  List<TourImage> geAlltTourImage();

  TourImage updateTourImage(TourImage tourImage, Long tourImageId);

  void deleteTourImage(Long tourImageId);
}