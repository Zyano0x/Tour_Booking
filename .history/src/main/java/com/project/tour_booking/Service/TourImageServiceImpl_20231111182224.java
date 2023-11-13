package com.project.tour_booking.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.Tour;
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
    Tour tour = tourRepository.findById(tourId).get();
    for (String image : tourImages) {
      TourImage tourImage = new TourImage(image, tourId);
      tourImage.setTour(tour);
      tourImageRepository.save(tourImage);
    }
  }

  // @Override
  // public void saveTourImage(TourImage tourImage) {
  // TourImage newTourImage = new TourImage(tourImage.getPath());
  // newTourImage.setTourId(tourImage.getTourId());
  // newTourImage.setTour(tourRepository.findById(tourImage.getTourId()).get());
  // tourImageRepository.save(newTourImage);
  // }

  @Override
  public TourImage getTourImage(Long tourImageId) {
    Optional<TourImage> tourImageOptional = tourImageRepository.findById(tourImageId);
    if (tourImageOptional.isPresent())
      return tourImageOptional.get();
    else
      throw new TourImageNotFoundException(tourImageId);
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
      updateTourImage.get().setPath(tourImage.getPath());
      return tourImageRepository.save(updateTourImage.get());
    } else
      throw new TourImageNotFoundException(tourImageId);
  }

  @Override
  public void deleteTourImage(Long tourImageId) {
    tourImageRepository.deleteById(tourImageId);
  }
}
