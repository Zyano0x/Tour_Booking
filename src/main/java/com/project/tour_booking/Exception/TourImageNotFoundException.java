package com.project.tour_booking.Exception;

public class TourImageNotFoundException extends RuntimeException {
  public TourImageNotFoundException(Long id) {
    super("Ảnh có mã '" + id + "' không tồn tại!");
  }
}