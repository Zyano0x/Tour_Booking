package com.project.tour_booking.Service.Admin.Booking;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Repository.BookingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
  private BookingRepository bookingRepository;

  @Override
  public void saveBooking(Booking booking, Long userId, Long tourId) {
    // TODO Auto-generated method stub

  }

  @Override
  public Booking getBooking(Long bookingId) {
    // TODO Auto-generated method stub
    return null;
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
