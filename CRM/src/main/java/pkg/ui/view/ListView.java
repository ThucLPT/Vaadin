package pkg.ui.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import pkg.backend.Person;
import pkg.backend.PersonService;
import pkg.ui.MainLayout;
import pkg.ui.PersonForm;

@Route(value = "", layout = MainLayout.class)
@PageTitle("CRM")
public class ListView extends VerticalLayout {
	private static final long serialVersionUID = 1L;
	private Grid<Person> grid = new Grid<>(Person.class);
	private Editor<Person> editor = grid.getEditor();
	private List<Button> list = new ArrayList<>();
	private TextField textField = new TextField();
	private PersonService personService;

	private TextField firstName = new TextField();
	private TextField lastName = new TextField();
	private Select<Person.Status> status = new Select<>(Person.Status.values());

	public ListView(PersonService personService) {
		this.personService = personService;
		setSizeFull();
		textFieldConfig();
		gridConfig();
		editorConfig();

		PersonForm form = new PersonForm(personService, grid);
		form.setVisible(false);
		HorizontalLayout layout = new HorizontalLayout(grid, form);
		layout.setSizeFull();

		Button button = new Button(VaadinIcon.PLUS.create(), click -> form.setVisible(!form.isVisible()));
		button.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
		HorizontalLayout head = new HorizontalLayout(textField, button);

		add(head, layout);
	}

	private void gridConfig() {
		grid.setSizeFull();
		grid.setItems(personService.findAll());
		grid.setColumns("firstName", "lastName", "status");
		grid.getColumns().forEach(col -> col.setAutoWidth(true));

		grid.addComponentColumn(item -> updateButton(item)).setEditorComponent(editorComponent()).setHeader("Update");
		grid.addComponentColumn(item -> deleteButton(item)).setHeader("Delete");

		grid.getColumnByKey("firstName").setEditorComponent(firstName);
		grid.getColumnByKey("lastName").setEditorComponent(lastName);
		grid.getColumnByKey("status").setEditorComponent(status);
	}

	private void textFieldConfig() {
		textField.setPlaceholder("Search by name...");
		textField.setClearButtonVisible(true);
		textField.setValueChangeMode(ValueChangeMode.LAZY);
		textField.addValueChangeListener(e -> {
			String value = textField.getValue().trim();
			if (value.length() > 0) {
				List<Person> list = personService.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(value, value);
				grid.setItems(list);
			} else
				grid.setItems(personService.findAll());
		});
	}

	private Button updateButton(Person person) {
		Button button = new Button(VaadinIcon.EDIT.create(), click -> editor.editItem(person));
		button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		button.setEnabled(!editor.isOpen());
		list.add(button);
		return button;
	}

	@SuppressWarnings("unchecked")
	private Button deleteButton(Person person) {
		Button button = new Button(VaadinIcon.TRASH.create(), click -> {
			ListDataProvider<Person> provider = (ListDataProvider<Person>) grid.getDataProvider();
			provider.getItems().remove(person);
			provider.refreshAll();
			personService.delete(person);
		});
		button.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
		return button;
	}

	private void editorConfig() {
		Binder<Person> binder = new BeanValidationBinder<>(Person.class);
		binder.bindInstanceFields(this);
		editor.setBinder(binder);
		editor.setBuffered(true);
		editor.addOpenListener(e -> list.forEach(button -> button.setEnabled(!editor.isOpen())));
		editor.addCloseListener(e -> list.forEach(button -> button.setEnabled(!editor.isOpen())));
		editor.addSaveListener(e -> personService.save(e.getItem()));
	}

	private Component editorComponent() {
		Button save = new Button("Save", e -> editor.save());
		Button cancel = new Button("Cancel", e -> editor.cancel());
		HorizontalLayout layout = new HorizontalLayout(save, cancel);
		return layout;
	}
}
