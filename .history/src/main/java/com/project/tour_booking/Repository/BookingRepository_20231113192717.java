package com.project.tour_booking.Repository;

import org.springframework.data.repository.CrudRepository;

import com.project.tour_booking.Entity.Booking;

public interface BookingRepository extends CrudRepository<Booking, Long> {

}
