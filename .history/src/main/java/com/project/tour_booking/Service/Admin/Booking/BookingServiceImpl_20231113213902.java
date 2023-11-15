package com.project.tour_booking.Service.Admin.Booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.Booking;
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
    booking.setUser(userRepository.findById(userId).get());
    booking.setTour(tourRepository.findById(tourId).get());
    booking.setBookingDate(LocalDate.now());
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
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Booking> getAllBookingByTourId(Long tourId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Booking getBookingByTourIdAndUserId(Long tourId, Long userId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void deleteBooking(Long bookingId) {
    // TODO Auto-generated method stub

  }
}
