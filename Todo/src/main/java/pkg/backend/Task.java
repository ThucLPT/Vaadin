package pkg.backend;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.*;

@Entity

@NoArgsConstructor
@Getter
@Setter
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	private String name;

	private boolean done;
}
