package com.project.tour_booking.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TourImageDTO {
  @NotBlank(message = "Đường dẫn ảnh không thể để trống!")
  private String path;

  @NotNull(message = "Mã tour không được để trống!")
  private Long tourId;
}
