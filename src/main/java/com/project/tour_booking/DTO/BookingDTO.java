package com.project.tour_booking.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class BookingDTO {
  @NotNull(message = "Số lượng người lớn không được để trống!")
  @PositiveOrZero(message = "Số lượng người lớn phải là số nguyên dương hoặc bằng 0!")
  private Integer quantityOfAdult;

  @NotNull(message = "Số lượng trẻ em không được để trống!")
  @PositiveOrZero(message = "Số lượng trẻ em phải là số nguyên dương hoặc bằng 0!")
  private Integer quantityOfChild;

  @NotNull(message = "Mã người dùng không được để trống!")
  @Min(value = 1, message = "Mã người dùng có giá trị bắt đầu từ '1'!")
  private Long userId;

  @NotNull(message = "Mã ngày khởi hành không được để trống!")
  @Min(value = 1, message = "Mã ngày khởi hành có giá trị bắt đầu từ '1'!")
  private Long departureDayId;

}
