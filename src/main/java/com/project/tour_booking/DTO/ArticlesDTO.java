package com.project.tour_booking.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArticlesDTO {
  @NotBlank(message = "Tiêu đề không được để trống!")
  private String title;

  @NotBlank(message = "Nội dung không được để trống!")
  private String content;

  private Long userId;
}
