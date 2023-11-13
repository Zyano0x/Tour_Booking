package com.project.tour_booking.Exception;

public class TourReviewNotFoundException extends RuntimeException {
  public TourReviewNotFoundException(Long id) {
    super("Đánh giá tour có mã đánh giá '" + id + "' không tồn tại!");
  }
}