package com.project.tour_booking.Exception;

public class DepartureDayNotFoundException extends RuntimeException {
  public DepartureDayNotFoundException(Long id) {
    super("Ngày khởi hành có mã '" + id + "' không tồn tại!");
  }
}
