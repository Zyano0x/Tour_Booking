package com.project.tour_booking.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.tour_booking.Entity.Booking;

public interface BookingRepository extends CrudRepository<Booking, Long> {
  List<Booking> findAllByUserId(Long userId);

  List<Booking> findAllByDepartureDayId(Long departureDayId);
}
