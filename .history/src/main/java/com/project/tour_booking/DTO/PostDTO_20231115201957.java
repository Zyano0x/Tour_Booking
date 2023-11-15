package com.project.tour_booking.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PostDTO {
  private String title;

  private String content;

  private Long userId;
}
