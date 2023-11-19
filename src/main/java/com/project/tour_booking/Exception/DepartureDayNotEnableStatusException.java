package com.project.tour_booking.Exception;

import java.time.LocalDate;

public class DepartureDayNotEnableStatusException extends RuntimeException {

  public DepartureDayNotEnableStatusException(LocalDate departureDay) {
    super("Ngày khởi hành '" + departureDay + "' đã hủy!");
  }
}
