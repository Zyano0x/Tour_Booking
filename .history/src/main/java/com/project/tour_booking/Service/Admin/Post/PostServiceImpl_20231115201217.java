package com.project.tour_booking.Service.Admin.Post;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.project.tour_booking.DTO.PostDTO;
import com.project.tour_booking.Entity.Post;
import com.project.tour_booking.Repository.PostRepository;
import com.project.tour_booking.Repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
  private PostRepository postRepository;
  private UserRepository userRepository;

  @Override
  public void savePost(PostDTO post) {
    post.setDateOfPosting(LocalDate.now());
    post.setStatus(true);
    post.setUser(userRepository.findById(post.getUserId()).get());
    postRepository.save(post);
  }
}