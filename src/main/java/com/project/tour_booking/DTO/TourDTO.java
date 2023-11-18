package com.project.tour_booking.DTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TourDTO {
  @NotBlank(message = "Tên của tour không được để trống!")
  private String name;

  @NotBlank(message = "Nội dung của tour không được để trống!")
  private String description;

  @NotBlank(message = "Dịch vụ không được để trống!")
  private String service;

  @NotBlank(message = "Thời gian của tour không được để trống!")
  private String time;

  @NotBlank(message = "Lịch trình của tour không được để trống!")
  private String schedule;

  @NotNull(message = "Giá tiền cho người lớn không được để trống!")
  private BigDecimal priceForAdult;

  @NotNull(message = "Giá tiền cho trẻ em không được để trống!")
  private BigDecimal priceForChildren;

  @NotBlank(message = "Điểm khởi hành không được để trống!")
  private String departurePoint;

  private Boolean status;

  @NotNull(message = "Điểm khởi hành không được để trống!")
  private Boolean isHot;

  @NotEmpty(message = "Ảnh tour không được để trống!")
  private List<String> images = new ArrayList<>();

  @NotNull(message = "Loại tour không được để trống không được để trống!")
  private Long totId;
}
