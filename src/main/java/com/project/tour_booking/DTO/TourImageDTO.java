package com.project.tour_booking.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TourImageDTO {
  @NotBlank(message = "Đường dẫn ảnh không thể để trống!")
  private String path;

  private Long tourId;
}
