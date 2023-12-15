package com.project.tour_booking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tour_booking.Entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  List<Booking> findAllByUserId(Long userId);

  List<Booking> findAllByDepartureDayId(Long departureDayId);
}
