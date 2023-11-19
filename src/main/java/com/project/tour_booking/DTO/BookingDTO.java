package com.project.tour_booking.DTO;

import lombok.Data;

@Data
public class BookingDTO {
  private Integer quantityOfAdult;

  private Integer quantityOfChild;

  private Long userId;

  private Long departureDayId;
}
