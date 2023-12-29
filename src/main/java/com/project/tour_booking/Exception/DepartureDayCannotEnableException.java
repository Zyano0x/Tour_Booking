package com.project.tour_booking.Exception;

import java.time.LocalDate;

public class DepartureDayCannotEnableException extends RuntimeException {

  public DepartureDayCannotEnableException(LocalDate departureDay) {
    super("Không thể tiến hành thao tác vì ngày khởi hành '" + departureDay + "' đã diễn ra!");
  }
}