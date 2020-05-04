package pkg.backend;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;

@Entity

@NoArgsConstructor
@Getter
@Setter
public class Person {
	public enum Status {
		ImportedLead, NotContacted, Contacted, Customer, ClosedLost
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String firstName, lastName;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Status status;

	public Person(String firstName, String lastName, Status status) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
	}
}
