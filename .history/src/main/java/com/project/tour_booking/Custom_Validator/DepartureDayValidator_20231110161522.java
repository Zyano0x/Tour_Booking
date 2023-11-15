package com.project.tour_booking.Custom_Validator;

import java.time.LocalDateTime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DepartureDayValidator implements ConstraintValidator<ValidDepartureDay, LocalDateTime> {
  @Override
  public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
    // TODO Auto-generated method stub
    return false;
  }
}
