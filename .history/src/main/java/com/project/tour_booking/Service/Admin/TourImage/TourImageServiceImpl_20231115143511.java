package com.project.tour_booking.Service.Admin.TourImage;

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
  public void saveTourImageFromTour(List<TourImage> tourImages, Long tourId) {
    Tour tour = tourRepository.findById(tourId).get();
    for (TourImage image : tourImages) {
      TourImage tourImage = new TourImage(image.getPath(), tourId);
      tourImage.setTour(tour);
      tourImageRepository.save(tourImage);
    }
  }

  @Override
  public void saveTourImage(TourImage tourImage) {
    TourImage newTourImage = new TourImage(tourImage.getPath(), tourImage.getTourIdForCrud());
    newTourImage.setTour(tourRepository.findById(tourImage.getTourIdForCrud()).get());
    tourImageRepository.save(newTourImage);
  }

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
      TourImage updateTourImage2 = updateTourImage.get();
      updateTourImage2.setPath(tourImage.getPath());
      updateTourImage2.setTour(tourRepository.findById(tourImage.getTourIdForCrud()).get());
      return tourImageRepository.save(updateTourImage2);
    } else
      throw new TourImageNotFoundException(tourImageId);
  }

  @Override
  public void deleteTourImage(Long tourImageId) {
    Optional<TourImage> tourImageOptional = tourImageRepository.findById(tourImageId);
    if (tourImageOptional.isPresent())
      tourImageRepository.deleteById(tourImageId);
    else
      throw new TourImageNotFoundException(tourImageId);
  }
}
