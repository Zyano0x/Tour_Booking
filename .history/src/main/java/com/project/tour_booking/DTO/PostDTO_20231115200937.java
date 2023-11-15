package com.project.tour_booking.DTO;

import java.time.LocalDate;

import com.project.tour_booking.Entity.User;

public class PostDTO {
  private String title;

  private String content;

  private LocalDate dateOfPosting;

  private Boolean status;

  private String userId;
}
