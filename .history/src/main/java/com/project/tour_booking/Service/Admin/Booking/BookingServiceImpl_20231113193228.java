package com.project.tour_booking.Service.Admin.Booking;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Repository.BookingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
  private BookingRepository bookingRepository;
}
