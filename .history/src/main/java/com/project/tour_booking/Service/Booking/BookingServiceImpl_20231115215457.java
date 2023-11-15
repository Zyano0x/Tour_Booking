package com.project.tour_booking.Service.Booking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tour_booking.DTO.BookingDTO;
import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Exception.BookingNotFoundException;
import com.project.tour_booking.Exception.TourNotFoundException;
import com.project.tour_booking.Exception.UserNotFoundException;
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
  public void saveBooking(BookingDTO bookingDTO) {
    Tour tour = tourRepository.findById(bookingDTO.getTourId()).get();
    Booking booking = new Booking();
    booking.setQuantityOfAdult(bookingDTO.getQuantityOfAdult());
    booking.setQuantityOfChild(bookingDTO.getQuantityOfChild());

    Optional<User> userOptional = userRepository.findById(bookingDTO.getUserId());
    if (userOptional.isPresent())
      booking.setUser(userOptional.get());
    else
      throw new UserNotFoundException(bookingDTO.getUserId());

    Optional<Tour> tourOptional = tourRepository.findById(bookingDTO.getTourId());
    if (tourOptional.isPresent())
      booking.setTour(tourOptional.get());
    else
      throw new TourNotFoundException(bookingDTO.getTourId());

    booking.setTour(tour);
    booking.setBookingDate(LocalDate.now());
    booking.setStatus(true);
    booking.setTotalPrice((new BigDecimal(booking.getQuantityOfAdult()).multiply(tour.getPriceForAdult()))
        .add(new BigDecimal(booking.getQuantityOfChild()).multiply(tour.getPriceForChildren())));

    Integer total = booking.getQuantityOfAdult() +
        booking.getQuantityOfChild();
    tour.setQuantity(tour.getQuantity() - total);

    tour.setImages(List.of("image"));
    tour.setDepartureDays(List.of(LocalDate.now()));

    tourRepository.save(tour);
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
