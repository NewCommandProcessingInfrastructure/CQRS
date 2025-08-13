package maker.checker.data;

import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonDTO implements Serializable {

  @NotNull
  @PositiveOrZero
  private Long id;

  @NotBlank
  @Length(min = 5, max = 100)
  private String firstName;

  @NotBlank
  @Length(min = 5, max = 100)
  private String lastName;
}
