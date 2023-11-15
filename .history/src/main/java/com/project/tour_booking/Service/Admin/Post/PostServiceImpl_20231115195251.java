package com.project.tour_booking.Service.Admin.Post;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.Post;
import com.project.tour_booking.Repository.PostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
  private PostRepository postRepository;

  @Override
  public void savePost(Post post, Long userId) {
    post.setDateOfPosting(LocalDate.now());
    post.setStatus(true);
    post.setUser();
    postRepository.save(post);
  }
}
