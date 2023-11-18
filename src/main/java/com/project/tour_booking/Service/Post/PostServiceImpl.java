package com.project.tour_booking.Service.Post;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.DTO.PostDTO;
import com.project.tour_booking.Entity.Post;
import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Exception.UserNotFoundException;
import com.project.tour_booking.Repository.PostRepository;
import com.project.tour_booking.Repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
  private PostRepository postRepository;
  private UserRepository userRepository;

  @Override
  public void savePost(PostDTO postDTO) {
    Post post = new Post();
    post.setContent(postDTO.getContent());
    post.setTitle(postDTO.getTitle());
    post.setDateOfPosting(LocalDate.now());
    post.setStatus(true);
    Optional<User> userOptional = userRepository.findById(postDTO.getUserId());
    if (userOptional.isPresent())
      post.setUser(userOptional.get());
    else
      throw new UserNotFoundException(postDTO.getUserId());
    postRepository.save(post);
  }
}
