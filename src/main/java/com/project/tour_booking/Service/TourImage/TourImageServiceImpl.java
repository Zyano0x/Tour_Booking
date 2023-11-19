package com.project.tour_booking.Service.TourImage;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.DTO.TourImageDTO;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Entity.TourImage;
import com.project.tour_booking.Exception.TourImageNotFoundException;
import com.project.tour_booking.Exception.TourNotFoundException;
import com.project.tour_booking.Repository.TourImageRepository;
import com.project.tour_booking.Repository.TourRepository;

import lombok.*;

@Service
@AllArgsConstructor
public class TourImageServiceImpl implements TourImageService {
  private TourImageRepository tourImageRepository;
  private TourRepository tourRepository;

  @Override
  public void saveTourImage(TourImageDTO tourImageDTO) {
    TourImage newTourImage = new TourImage(tourImageDTO.getPath());
    Optional<Tour> tourOptional = tourRepository.findById(tourImageDTO.getTourId());
    if (tourOptional.isPresent())
      newTourImage.setTour(tourOptional.get());
    else
      throw new TourNotFoundException(tourImageDTO.getTourId());
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
  public List<TourImage> getTourImages() {
    return (List<TourImage>) tourImageRepository.findAll();
  }

  @Override
  public TourImage updateTourImage(TourImageDTO tourImageDTO, Long tourImageId) {
    // Kiểm tra tồn tại
    Optional<TourImage> TourImageOptional = tourImageRepository.findById(tourImageId);
    if (TourImageOptional.isPresent()) {
      TourImage updateTourImage = TourImageOptional.get();

      updateTourImage.setPath(tourImageDTO.getPath());

      // Kiểm tra tồn tại
      Optional<Tour> tourOptional = tourRepository.findById(tourImageDTO.getTourId());
      if (tourOptional.isPresent())
        updateTourImage.setTour(tourOptional.get());
      else
        throw new TourNotFoundException(tourImageDTO.getTourId());
      return tourImageRepository.save(updateTourImage);
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
