package com.java.validation.DemoValidation.data;

import com.java.validation.DemoValidation.annotation.CurrencyCodeMatches;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@CurrencyCodeMatches
public class CurrencyCreateRequest implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @NotBlank(message = "{currency.code.notBlank}")
  @Pattern(regexp = "^[A-Z]{3}$", message = "{currency.code.invalidFormat}")
  private String code;

  @NotBlank(message = "{currency.name.notBlank}")
  private String name;

  @NotNull(message = "{currency.decimalPlaces.notNull}")
  @Min(value = 0, message = "{currency.decimalPlaces.min}")
  @Max(value = 5, message = "{currency.decimalPlaces.max}")
  private Integer decimalPlaces;

  @Min(value = 0, message = "{currency.inMultiplesOf.min}")
  @Max(value = 1000, message = "{currency.inMultiplesOf.max}")
  private Integer inMultiplesOf;

  @NotBlank(message = "{currency.displaySymbol.notBlank}")
  private String displaySymbol;

  @NotBlank(message = "{currency.nameCode.notBlank}")
  @Pattern(regexp = "^[A-Za-z]+\\.[A-Z]{3}$", message = "{currency.nameCode.invalidFormat}")
  private String nameCode;
}
