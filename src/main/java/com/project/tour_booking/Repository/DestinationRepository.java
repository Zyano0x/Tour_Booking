package com.project.tour_booking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tour_booking.Entity.Destination;

public interface DestinationRepository extends JpaRepository<Destination, Long> {

}
