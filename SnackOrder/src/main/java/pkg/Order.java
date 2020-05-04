package pkg;

import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
	@NotBlank
	private String name, snack;

	@NotNull
	@Positive
	private Integer quantity;
}
