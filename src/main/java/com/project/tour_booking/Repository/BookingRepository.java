package com.project.tour_booking.Repository;

import com.project.tour_booking.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  List<Booking> findAllByUserId(Long userId);

  List<Booking> findAllByDepartureDayId(Long departureDayId);

  Optional<Booking> findByTransactionCode(Long transactionCode);
}
