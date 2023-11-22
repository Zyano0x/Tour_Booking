package com.project.tour_booking.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ArticlesDTO {
  @NotBlank(message = "Tiêu đề không được để trống!")
  private String title;

  @NotBlank(message = "Nội dung không được để trống!")
  private String content;

  @NotNull(message = "Mã người dùng không được để trống!")
  @Min(value = 1, message = "Mã người dùng có giá trị bắt đầu từ '1'!")
  private Long userId;
}
