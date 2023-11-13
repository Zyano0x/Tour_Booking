package com.project.tour_booking.CustomValidator;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ListDepartureDayValidator implements ConstraintValidator<ValidDepartureDay, List<LocalDate>> {
  @Override
  public boolean isValid(List<LocalDate> values, ConstraintValidatorContext context) {
    for (LocalDate localDate : values) {
      if (!localDate.isAfter(LocalDate.now()))
        return false;
    }
    return true;
  }
}
