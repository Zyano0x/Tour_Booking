package com.project.tour_booking.Exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(Long id) {
    super("Người dùng có id '" + id + "' không tồn tại");
  }
}
