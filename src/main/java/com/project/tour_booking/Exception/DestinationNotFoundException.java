package com.project.tour_booking.Exception;

public class DestinationNotFoundException extends RuntimeException {
  public DestinationNotFoundException(Long id) {
    super("Địa điểm có mã '" + id + "' không tồn tại!");
  }
}
