package com.project.tour_booking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tour_booking.Entity.TypeOfTour;

public interface TypeOfTourRepository extends JpaRepository<TypeOfTour, Long> {

}
