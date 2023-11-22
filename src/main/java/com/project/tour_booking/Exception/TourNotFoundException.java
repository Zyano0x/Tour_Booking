package com.project.tour_booking.Exception;

public class TourNotFoundException extends RuntimeException {
  public TourNotFoundException(Long id) {
    super("Tour có mã '" + id + "' không tồn tại!");
  }
}