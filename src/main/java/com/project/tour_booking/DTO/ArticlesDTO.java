package com.project.tour_booking.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticlesDTO {
  @NotBlank(message = "Tiêu đề không được để trống!")
  private String title;

  @NotBlank(message = "Mô tả không được để trống!")
  private String description;

  @NotBlank(message = "Nội dung không được để trống!")
  private String content;

  @NotBlank(message = "Thumbnail không được để trống!")
  private String thumbnail;

  @NotNull(message = "Mã người dùng không được để trống!")
  @Min(value = 1, message = "Mã người dùng có giá trị bắt đầu từ '1'!")
  private Long userId;
}
