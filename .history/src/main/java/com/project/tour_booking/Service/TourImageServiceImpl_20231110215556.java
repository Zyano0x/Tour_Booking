package com.project.tour_booking.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.TourImage;
import com.project.tour_booking.Exception.TourImageNotFoundException;
import com.project.tour_booking.Repository.TourImageRepository;
import com.project.tour_booking.Repository.TourRepository;

import lombok.*;

@Service
@AllArgsConstructor
public class TourImageServiceImpl implements TourImageService {
  private TourImageRepository tourImageRepository;
  private TourRepository tourRepository;

  @Override
  public void saveTourImageFromTour(List<String> tourImages, Long tourId) {
    for (String image : tourImages) {
      TourImage tourImage = new TourImage(image);
      tourImage.setTour(tourRepository.findById(tourId).get());
      tourImageRepository.save(tourImage);
    }
  }

  @Override
  public void saveTourImage(TourImage tourImage) {
    tourImageRepository.save(tourImage);
  }

  @Override
  public TourImage getTourImage(Long tourImageId) {
    return null;
  }

  @Override
  public List<TourImage> getTourImageByTourId(Long tourId) {
    return tourImageRepository.findAllByTourId(tourId);
  }

  @Override
  public List<TourImage> geAlltTourImage() {
    return (List<TourImage>) tourImageRepository.findAll();
  }

  @Override
  public TourImage updateTourImage(TourImage tourImage, Long tourImageId) {
    Optional<TourImage> updateTourImage = tourImageRepository.findById(tourImageId);
    if (updateTourImage.isPresent()) {
      TourImage updateTourImage2 = updateTourImage.get();
      updateTourImage2.setPath(tourImage.getPath());
      updateTourImage2.setTour(tourRepository.findById(tourImage.getId()).get());
      return tourImageRepository.save(updateTourImage2);
    } else
      throw new TourImageNotFoundException(tourImageId);
  }

  @Override
  public void deleteTourImage(Long tourImageId) {
    tourImageRepository.deleteById(tourImageId);
  }
}
