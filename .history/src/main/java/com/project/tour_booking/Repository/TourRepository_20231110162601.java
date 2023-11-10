package com.project.tour_booking.Repository;

import org.springframework.data.repository.CrudRepository;

import com.project.tour_booking.Entity.Tour;

public interface TourRepository extends CrudRepository<Tour, Long> {

}
