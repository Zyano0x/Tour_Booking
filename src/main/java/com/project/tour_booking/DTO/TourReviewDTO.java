package com.project.tour_booking.DTO;

import lombok.Data;

@Data
public class TourReviewDTO {
  private String content;

  private Integer vote;

  private Long userId;

  private Long tourId;
}
