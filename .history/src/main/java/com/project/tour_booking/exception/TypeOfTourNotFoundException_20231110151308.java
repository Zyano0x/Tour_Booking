package com.project.tour_booking.exception;

public class TypeOfTourNotFoundException extends RuntimeException {
  public TypeOfTourNotFoundException(Long id) {
    super("Loại tour id '" + id + "' không tồn tại!");
  }
}
