package com.project.tour_booking.Exception;

public class TourNotEnableException extends RuntimeException {
  public TourNotEnableException(Long tourId) {
    super("Tour có mã '" + tourId + "' đã hủy!");
  }
}
