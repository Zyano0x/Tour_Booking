package com.project.tour_booking.Exception;

public class DepartureDayNotFoundException extends RuntimeException {
  public DepartureDayNotFoundException(Long id) {
    super("Ngày khởi hành có id '" + id + "' không tồn tại!");
  }
}
