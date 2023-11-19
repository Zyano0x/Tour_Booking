package com.project.tour_booking.Service.TourImage;

import java.util.List;

import com.project.tour_booking.DTO.TourImageDTO;
import com.project.tour_booking.Entity.TourImage;

public interface TourImageService {
  void saveTourImage(TourImageDTO tourImageDTO);

  TourImage getTourImage(Long tourImageId);

  List<TourImage> getTourImageByTourId(Long tourId);

  List<TourImage> getTourImages();

  TourImage updateTourImage(TourImageDTO tourImageDTO, Long tourImageId);

  void deleteTourImage(Long tourImageId);
}
