package com.project.tour_booking.Exception;

public class TourNotFoundException extends RuntimeException {
  public TourNotFoundException(Long id) {
    super("Tour id '" + id + "' không tồn tại!");
  }
}