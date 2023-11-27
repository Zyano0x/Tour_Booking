package com.project.tour_booking.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlesDTO {
  @NotBlank(message = "Tiêu đề không được để trống!")
  private String title;

  @NotBlank(message = "Nội dung không được để trống!")
  private String content;

  private Long userId;
}
