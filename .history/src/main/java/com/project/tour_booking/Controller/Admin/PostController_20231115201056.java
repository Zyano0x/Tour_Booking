package com.project.tour_booking.Controller.Admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.DTO.PostDTO;
import com.project.tour_booking.Entity.Post;
import com.project.tour_booking.Service.Admin.Post.PostService;

@RestController
@RequestMapping("/api/admin/post")
public class PostController {
  private PostService postService;

  @PostMapping("")
  public ResponseEntity<String> savePost(@RequestBody PostDTO post) {
    postService.savePost(post, userId);
    return new ResponseEntity<>("ĐĂNG BÀI THÀNH CÔNG!", HttpStatus.CREATED);
  }
}