package pkg.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import pkg.backend.Person;
import pkg.backend.PersonService;

public class PersonForm extends FormLayout {
	private static final long serialVersionUID = 1L;

	private TextField firstName = new TextField("First Name");
	private TextField lastName = new TextField("Last Name");
	private Select<Person.Status> status = new Select<>(Person.Status.values());
	private Person person = new Person();

	public PersonForm(PersonService personService, Grid<Person> grid) {
		Binder<Person> binder = new BeanValidationBinder<>(Person.class);
		binder.bindInstanceFields(this);
		binder.setBean(person);

		status.setLabel("Status");
		Button button = new Button("Save", click -> {
			if (binder.validate().isOk()) {
				personService.save(person);
				person = new Person();
				binder.setBean(person);
				grid.setItems(personService.findAll());
			}
		});
		button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		add(firstName, lastName, status, button);
	}
}
