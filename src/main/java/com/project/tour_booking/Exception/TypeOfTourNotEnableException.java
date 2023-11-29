package com.project.tour_booking.Exception;

public class TypeOfTourNotEnableException extends RuntimeException {
  public TypeOfTourNotEnableException(Long totId) {
    super("Loại tour có mã '" + totId + "' đã bị vô hiệu hóa!");
  }
}
