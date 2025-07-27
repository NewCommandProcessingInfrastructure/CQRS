package com.java.validation.DemoValidation.annotation;

import com.java.validation.DemoValidation.data.CurrencyCreateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class CurrencyNameCodeMatchValidator implements ConstraintValidator<CurrencyCodeMatches, CurrencyCreateRequest> {

  @Override
  public boolean isValid(CurrencyCreateRequest request, ConstraintValidatorContext constraintValidatorContext) {
    if (request == null) return true;

    String code = request.getCode();
    String nameCode = request.getNameCode();

    if (StringUtils.isAnyBlank(code, nameCode)) {
      return true; // Other validators will catch null/blank
    }

    String[] parts = nameCode.split("\\.");
    return parts.length == 2 && code.equals(parts[1]);
  }
}
