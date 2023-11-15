package com.project.tour_booking.Exception;

public class TypeOfTourNotFoundException extends RuntimeException {
  public TypeOfTourNotFoundException(Long id) {
    super("Loại tour id '" + id + "' không tồn tại!");
  }
}
