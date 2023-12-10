package com.project.tour_booking.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DestinationDTO {
  @NotBlank(message = "Tên địa điểm không được để trống!")
  private String name;

  @NotBlank(message = "Thumnail địa điểm không được để trống!")
  private String thumbnail;

  @NotNull(message = "Is hot không được để trống!")
  private Boolean isHot;
}
