package com.project.tour_booking.CustomValidator;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TourImagesValidator implements ConstraintValidator<ValidTourImages, List<String>> {

  @Override
  public boolean isValid(List<String> value, ConstraintValidatorContext context) {
    return value.size() > 0 ? true : false;
  }
}