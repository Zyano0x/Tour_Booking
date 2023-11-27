package com.project.tour_booking.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DestinationDTO {
  @NotBlank(message = "Tên địa điểm không được để trống!")
  private String name;

  @NotBlank(message = "Thumnail địa điểm không được để trống!")
  private String thumbnail;
}
