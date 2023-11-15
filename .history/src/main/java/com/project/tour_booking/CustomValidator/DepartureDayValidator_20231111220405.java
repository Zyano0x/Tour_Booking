package com.project.tour_booking.CustomValidator;

import java.time.LocalDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DepartureDayValidator implements ConstraintValidator<ValidDepartureDay, LocalDate> {

  @Override
  public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
    return value.isAfter(LocalDate.now());
  }
}
