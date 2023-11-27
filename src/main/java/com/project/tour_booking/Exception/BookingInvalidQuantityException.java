package com.project.tour_booking.Exception;

public class BookingInvalidQuantityException extends RuntimeException {

  public BookingInvalidQuantityException() {
    super("Số lượng không hợp lệ (tổng số lượng phải lớn hơn '1')!");
  }
}
