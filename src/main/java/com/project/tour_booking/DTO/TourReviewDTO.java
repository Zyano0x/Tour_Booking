package com.project.tour_booking.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TourReviewDTO {
  @NotNull(message = "Nội dung không được để trống!")
  private String content;

  @NotNull(message = "Vote không được để trống!")
  private Integer vote;

  @NotNull(message = "Mã người dùng không được để trống!")
  @Min(value = 1, message = "Mã người dùng có giá trị bắt đầu từ '1'!")
  private Long userId;

  @NotNull(message = "Mã tour không được để trống!")
  @Min(value = 1, message = "Mã tour có giá trị bắt đầu từ '1'!")
  private Long tourId;
}
