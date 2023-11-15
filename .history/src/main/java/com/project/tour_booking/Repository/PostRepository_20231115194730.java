package com.project.tour_booking.Repository;

import org.springframework.data.repository.CrudRepository;

import com.project.tour_booking.Entity.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

}
