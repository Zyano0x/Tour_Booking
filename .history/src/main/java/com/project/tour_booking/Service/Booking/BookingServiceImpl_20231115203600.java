package com.project.tour_booking.Service.Booking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Exception.BookingNotFoundException;
import com.project.tour_booking.Repository.BookingRepository;
import com.project.tour_booking.Repository.TourRepository;
import com.project.tour_booking.Repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
  private BookingRepository bookingRepository;
  private TourRepository tourRepository;
  private UserRepository userRepository;

  @Override
  public void saveBooking(Booking booking, Long userId, Long tourId) {
    Tour tour = tourRepository.findById(tourId).get();
    booking.setUser(userRepository.findById(userId).get());
    booking.setTour(tour);
    booking.setBookingDate(LocalDate.now());
    booking.setStatus(true);
    booking.setTotalPrice((new BigDecimal(booking.getQuantityOfAdult()).multiply(tour.getPriceForAdult()))
        .add(new BigDecimal(booking.getQuantityOfChild()).multiply(tour.getPriceForChildren())));

    Tour updateTour = tourRepository.findById(tourId).get();
    Integer total = booking.getQuantityOfAdult() +
        booking.getQuantityOfChild();
    updateTour.setQuantity(updateTour.getQuantity() - total);

    List<String> images = new ArrayList<>();
    images.add("image");
    updateTour.setImages(images);

    List<LocalDate> departureDays = new ArrayList<>();
    departureDays.add(LocalDate.now());
    updateTour.setDepartureDays(departureDays);
    tourRepository.save(updateTour);

    bookingRepository.save(booking);
  }

  @Override
  public Booking getBooking(Long bookingId) {
    Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
    if (bookingOptional.isPresent())
      return bookingOptional.get();
    else
      throw new BookingNotFoundException(bookingId);
  }

  @Override
  public List<Booking> getAllBookingByUserId(Long userId) {
    return bookingRepository.findAllByUserId(userId);
  }

  @Override
  public List<Booking> getAllBookingByTourId(Long tourId) {
    return bookingRepository.findAllByTourId(tourId);
  }

  @Override
  public List<Booking> getBookingByUserIdAndTourId(Long userId, Long tourId) {
    return bookingRepository.findAllByUserIdAndTourId(userId, tourId);
  }

  @Transactional
  @Override
  public void updateStatusBooking(Long bookingId) {
    Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
    if (bookingOptional.isPresent()) {
      Booking updateStatusBooking = bookingOptional.get();
      Tour updateTour = tourRepository.findById(updateStatusBooking.getTour().getId()).get();
      Integer total = updateStatusBooking.getQuantityOfAdult() +
          updateStatusBooking.getQuantityOfChild();
      if (updateStatusBooking.getStatus()) {
        updateStatusBooking.setStatus(false);
        updateTour.setQuantity(updateTour.getQuantity() + total);
      } else {
        updateStatusBooking.setStatus(true);
        updateTour.setQuantity(updateTour.getQuantity() - total);
      }

      updateTour.setImages(List.of("image"));
      updateTour.setDepartureDays(List.of(LocalDate.now()));

      tourRepository.save(updateTour);
      bookingRepository.save(updateStatusBooking);
    } else
      throw new BookingNotFoundException(bookingId);
  }

  @Override
  public void deleteBooking(Long bookingId) {
    Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
    if (bookingOptional.isPresent()) {
      Booking updateStatusBooking = bookingOptional.get();
      updateStatusBooking.setStatus(false);
      bookingRepository.save(updateStatusBooking);
    } else
      throw new BookingNotFoundException(bookingId);
  }
}