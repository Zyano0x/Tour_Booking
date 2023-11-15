package com.project.tour_booking.Service.TourReview;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.DTO.TourReviewDTO;
import com.project.tour_booking.Entity.TourReview;
import com.project.tour_booking.Exception.TourReviewNotFoundException;
import com.project.tour_booking.Repository.TourRepository;
import com.project.tour_booking.Repository.TourReviewRepository;
import com.project.tour_booking.Repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TourReviewServiceImpl implements TourReviewService {
  private TourReviewRepository tourReviewRepository;
  private TourRepository tourRepository;
  private UserRepository userRepository;

  @Override
  public void saveTourReview(TourReviewDTO tourReviewDTO) {
    TourReview tourReview = new TourReview();
    tourReview.setContent(tourReviewDTO.getContent());
    tourReview.setVote(tourReviewDTO.getVote());
    tourReview.setUser(userRepository.findById(tourReviewDTO.getUserId()).get());
    tourReview.setTour(tourRepository.findById(tourReviewDTO.getTourId()).get());
    tourReview.setDateOfPosting(LocalDate.now());
    tourReviewRepository.save(tourReview);
  }

  @Override
  public TourReview getTourReview(Long tourReviewId) {
    Optional<TourReview> tourReviewOptional = tourReviewRepository.findById(tourReviewId);
    if (tourReviewOptional.isPresent())
      return tourReviewOptional.get();
    else
      throw new TourReviewNotFoundException(tourReviewId);
  }

  @Override
  public TourReview getTourReviewByTourIdAndUserId(Long tourId, Long userId) {
    Optional<TourReview> tourReviewOptional = tourReviewRepository.findByTourIdAndUserId(tourId, userId);
    if (tourReviewOptional.isPresent())
      return tourReviewOptional.get();
    else
      throw new TourReviewNotFoundException(tourId, userId);
  }

  @Override
  public List<TourReview> getAllTourReviewByTourId(Long tourId) {
    return tourReviewRepository.findAllByTourId(tourId);
  }

  @Override
  public List<TourReview> getAllTourReviewByUserId(Long userId) {
    return tourReviewRepository.findAllByUserId(userId);
  }

  @Override
  public TourReview updateTourReview(TourReview tourReview, Long tourReviewId) {
    Optional<TourReview> tourReviewOptional = tourReviewRepository.findById(tourReviewId);
    if (tourReviewOptional.isPresent()) {
      return tourReviewRepository.save(tourReview);
    }
  }

  @Override
  public void deleteTourReview(Long tourReviewId) {
    Optional<TourReview> tourReviewOptional = tourReviewRepository.findById(tourReviewId);
    if (tourReviewOptional.isPresent())
      tourReviewRepository.deleteById(tourReviewId);
    else
      throw new TourReviewNotFoundException(tourReviewId);
  }
}
