package com.project.tour_booking.Repository;

import org.springframework.data.repository.CrudRepository;

import com.project.tour_booking.Entity.Destination;

public interface DestinationRepository extends CrudRepository<Destination, Long> {

}
