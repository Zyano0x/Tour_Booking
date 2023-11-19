package com.project.tour_booking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tour_booking.Entity.Articles;

public interface ArticlesRepository extends JpaRepository<Articles, Long> {

}
