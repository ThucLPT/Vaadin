package pkg.frontend;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import lombok.Getter;
import pkg.backend.Task;
import pkg.backend.TaskService;

public class TaskLayout extends HorizontalLayout {
	private static final long serialVersionUID = 1L;

	@Getter
	private Binder<Task> binder = new BeanValidationBinder<>(Task.class);

	private Checkbox done = new Checkbox();
	private TextField name = new TextField();

	public TaskLayout(Task task, TaskService taskService) {
		setDefaultVerticalComponentAlignment(Alignment.CENTER);
		setWidth("50%");

		add(done, name);
		expand(name);

		binder.bindInstanceFields(this);
		binder.setBean(task);
		binder.addValueChangeListener(listener -> taskService.save(task));
	}
}
