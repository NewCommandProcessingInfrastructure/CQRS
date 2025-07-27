package com.java.validation.DemoValidation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CurrencyNameCodeMatchValidator.class)
public @interface CurrencyCodeMatches {
  String message() default "{currency.nameCode.mismatch}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
