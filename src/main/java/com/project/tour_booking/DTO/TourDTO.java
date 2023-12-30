package com.project.tour_booking.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourDTO {
    @NotBlank(message = "Tên của tour không được để trống!")
    private String name;

    @NotBlank(message = "Thumnail tour không được để trống!")
    private String thumbnail;

    @NotBlank(message = "Nội dung của tour không được để trống!")
    private String description;

    @NotBlank(message = "Dịch vụ không được để trống!")
    private String service;

    @NotBlank(message = "Thời gian của tour không được để trống!")
    private String time;

    @NotBlank(message = "Lịch trình của tour không được để trống!")
    private String schedule;

    @NotNull(message = "Giá tiền cho người lớn không được để trống!")
    @PositiveOrZero(message = "Giá tiền cho người lớn phải là số dương hoặc bằng 0!")
    private BigDecimal priceForAdult;

    @NotNull(message = "Giá tiền cho trẻ em không được để trống!")
    @PositiveOrZero(message = "Giá tiền cho trẻ em phải là số dương hoặc bằng 0!")
    private BigDecimal priceForChildren;

    @NotBlank(message = "Điểm khởi hành không được để trống!")
    private String departurePoint;

    @NotNull(message = "Trạng thái không được để trống!")
    private Boolean status;

    @NotNull(message = "Is hot không được để trống!")
    private Boolean isHot;

    @NotEmpty(message = "Ảnh tour không được để trống!")
    private List<String> images = new ArrayList<>();

    @NotNull(message = "Loại tour không được để trống không được để trống!")
    private Long totId;

    @NotNull(message = "Địa điểm không được để trống không được để trống!")
    private Long destinationId;
}
