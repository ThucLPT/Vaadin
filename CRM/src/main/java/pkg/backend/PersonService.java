package pkg.backend;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
	@Autowired
	private PersonRepository personRepository;

	public Person save(Person person) {
		return personRepository.save(person);
	}

	public void delete(Person person) {
		personRepository.delete(person);
	}

	public List<Person> findAll() {
		return personRepository.findAll();
	}

	public List<Person> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName) {
		return personRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName);
	}

	public Map<String, Long> getStats() {
		return Arrays.asList(Person.Status.values()).stream().collect(Collectors.toMap(status -> status.name(), status -> personRepository.countByStatus(status)));
	}

	public Long count() {
		return personRepository.count();
	}
}
