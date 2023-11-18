package com.project.tour_booking.Exception;

public class BookingStatusException extends RuntimeException {

  public BookingStatusException() {
    super("Không thể tực hiện thao tác vì đơn đặt tour này đã hủy!");
  }
}
