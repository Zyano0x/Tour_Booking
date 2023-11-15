package com.project.tour_booking.Exception;

public class BookingNotFoundException extends RuntimeException {

  public BookingNotFoundException(Long id) {
    super("Đơn đặt tour có id '" + id + "' không tồn tại");
  }
}
