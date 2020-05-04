package pkg.backend;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

@Component
public class PersonInit implements ApplicationRunner {
	@Autowired
	private PersonRepository personRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Person.Status[] statuses = Person.Status.values();
		Random random = new Random();
		Faker faker = new Faker(new Locale("en"), random);
		List<Person> list = new ArrayList<>();
		for (int i = 0; i < 30; i++) {
			int pick = random.nextInt(statuses.length);
			list.add(new Person(faker.name().firstName(), faker.name().lastName(), statuses[pick]));
		}
		personRepository.saveAll(list);
	}
}
