package com.project.tour_booking.Service.TourReview;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.project.tour_booking.DTO.TourReviewDTO;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Entity.TourReview;
import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Exception.TourNotFoundException;
import com.project.tour_booking.Exception.TourReviewNotFoundException;
import com.project.tour_booking.Exception.UserNotFoundException;
import com.project.tour_booking.Repository.TourRepository;
import com.project.tour_booking.Repository.TourReviewRepository;
import com.project.tour_booking.Repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@RequiredArgsConstructor
public class TourReviewServiceImpl implements TourReviewService {
    private final TourReviewRepository tourReviewRepository;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public TourReview saveTourReview(TourReviewDTO tourReviewDTO) {
        Optional<User> userOptional = userRepository.findById(tourReviewDTO.getUserId());
        Optional<Tour> tourOptional = tourRepository.findById(tourReviewDTO.getTourId());

        if (userOptional.isPresent() && tourOptional.isPresent()) {
            TourReview tourReview = new TourReview();
            tourReview.setContent(tourReviewDTO.getContent());
            tourReview.setVote(tourReviewDTO.getVote());
            tourReview.setTour(tourOptional.get());
            tourReview.setUser(userOptional.get());
            tourReview.setDateOfPosting(LocalDate.now());
            tourReviewRepository.save(tourReview);
            return tourReview;
        } else {
            throw new RuntimeException("Something went wrong.");
        }
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
        Optional<Tour> tourOptional = tourRepository.findById(tourId);
        if (tourOptional.isPresent())
            return tourReviewRepository.findAllByTourId(tourId);
        else
            throw new TourNotFoundException(tourId);
    }

    @Override
    public List<TourReview> getAllTourReviewByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent())
            return tourReviewRepository.findAllByUserId(userId);
        else
            throw new UserNotFoundException(userId);
    }

    @Override
    public TourReview updateTourReview(TourReviewDTO tourReviewDTO, Long tourReviewId) {
        Optional<TourReview> tourReviewOptional = tourReviewRepository.findById(tourReviewId);
        if (tourReviewOptional.isPresent()) {
            TourReview updateTourReview = tourReviewOptional.get();
            updateTourReview.setContent(tourReviewDTO.getContent());
            updateTourReview.setVote(tourReviewDTO.getVote());
            updateTourReview.setEditDate(LocalDate.now());
            tourReviewRepository.save(updateTourReview);
            return updateTourReview;
        } else
            throw new TourReviewNotFoundException(tourReviewId);
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
