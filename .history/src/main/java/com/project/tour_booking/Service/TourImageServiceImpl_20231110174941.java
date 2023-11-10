package com.project.tour_booking.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Repository.TourImageRepository;

import lombok.*;

@Service
@AllArgsConstructor
public class TourImageServiceImpl implements TourImageService {
  private TourImageRepository tourImageRepository;

  @Override
  public void saveTourImage(List<String> tourImages) {
    for (String image : tourImages) {
      tourImageRepository.save(image);
    }
  }
}
