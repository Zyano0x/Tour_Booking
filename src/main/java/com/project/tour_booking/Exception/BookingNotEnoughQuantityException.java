package com.project.tour_booking.Exception;

import java.time.LocalDate;

public class BookingNotEnoughQuantityException extends RuntimeException {

  public BookingNotEnoughQuantityException(LocalDate departureDay) {
    super("Số lượng chỗ còn lại của tour khởi hành ngày '" + departureDay + "' không đủ!");
  }
}
