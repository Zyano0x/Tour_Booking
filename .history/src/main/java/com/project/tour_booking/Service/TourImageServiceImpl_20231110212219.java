package com.project.tour_booking.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.TourImage;
import com.project.tour_booking.Repository.TourImageRepository;
import com.project.tour_booking.Repository.TourRepository;

import lombok.*;

@Service
@AllArgsConstructor
public class TourImageServiceImpl implements TourImageService {
  private TourImageRepository tourImageRepository;
  private TourRepository tourRepository;

  @Override
  public void saveTourImage(List<String> tourImages, Long tourId) {
    for (String image : tourImages) {
      TourImage tourImage = new TourImage(image);
      tourImage.setTour(tourRepository.findById(tourId).get());
      tourImageRepository.save(tourImage);
    }
  }

  @Override
  public List<TourImage> getTourImageByTourId(Long tourId) {
    return tourImageRepository.findAllByTourId(tourId);
  }

  @Override
  public TourImage updateTourImage(TourImage tourImage, Long id) {

    return tourImageRepository.save(updateTourImage);
  }
}
