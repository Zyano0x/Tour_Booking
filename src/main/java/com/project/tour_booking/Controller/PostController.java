package com.project.tour_booking.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.DTO.PostDTO;
import com.project.tour_booking.Service.Post.PostService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PostController {
  private PostService postService;

  @PostMapping("/post")
  public ResponseEntity<String> savePost(@Valid @RequestBody PostDTO postDTO) {
    postService.savePost(postDTO);
    return new ResponseEntity<>("ĐĂNG BÀI THÀNH CÔNG!", HttpStatus.CREATED);
  }
}
