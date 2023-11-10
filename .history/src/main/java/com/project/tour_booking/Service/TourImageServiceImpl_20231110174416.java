package com.project.tour_booking.Service;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Repository.TourImageRepository;

import lombok.*;

@Service
@AllArgsConstructor
public class TourImageServiceImpl implements TourImageService {
  private TourImageRepository tourImageRepository;

}
