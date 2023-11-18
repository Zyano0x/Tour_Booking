package com.project.tour_booking.Exception;

public class BookingQuantityException extends RuntimeException {

  public BookingQuantityException() {
    super("Số lượng chỗ còn lại của tour không đủ!");
  }
}
