package com.project.tour_booking.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.tour_booking.CustomValidator.ValidDepartureDay;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepartureDayDTO {
  @NotNull(message = "Số lượng khách không được để trống!")
  private Integer quantity;

  @JsonFormat(pattern = "dd-MM-yyyy")
  @ValidDepartureDay(message = "Ngày khởi hành phải lớn hơn thời điểm tạo tour!")
  private LocalDate departureDay;

  private Boolean status;

  private Long tourId;
}
