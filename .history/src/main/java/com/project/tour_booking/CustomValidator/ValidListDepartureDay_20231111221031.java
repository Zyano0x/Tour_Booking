package com.project.tour_booking.CustomValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DepartureDayValidator.class)
public @interface ValidListDepartureDay {
  String message() default "Invalid Data";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}