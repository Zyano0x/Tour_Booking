package com.project.tour_booking.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeOfTourDTO {
  @NotBlank(message = "Tên loại tour không được để trống")
  private String name;

  @NotBlank(message = "Mô tả loại tour không được để trống")
  private String description;
}
