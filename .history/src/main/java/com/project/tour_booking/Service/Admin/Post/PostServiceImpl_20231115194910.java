package com.project.tour_booking.Service.Admin.Post;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Repository.PostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
  private PostRepository postRepository;

}
