package maker.checker.data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTransactionRequest implements Serializable {

  @NotBlank
  private String description;

  @Min(value = 1, message = "Amount must be Positive")
  private double amount;
}
