package pkg.frontend;

import java.util.Arrays;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import pkg.backend.Task;
import pkg.backend.TaskService;

@Route("")
@PageTitle("Todo")
public class TaskView extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	private TaskService taskService;
	private TaskList taskList;

	private TextField name = new TextField();
	private Task task = new Task();

	public TaskView(TaskService taskService) {
		this.taskService = taskService;

		setSizeFull();
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		getStyle().set("overflow", "auto");

		Button delBtn = new Button("Delete completed tasks", click -> {
			taskService.deleteByDone();
			TaskLayout[] layouts = taskList.getChildren().toArray(TaskLayout[]::new);
			TaskLayout[] doneLayouts = Arrays.stream(layouts).filter(layout -> layout.getBinder().getBean().isDone()).toArray(TaskLayout[]::new);
			taskList.remove(doneLayouts);
		});
		delBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

		taskList = new TaskList(taskService.findAll(), taskService);

		add(new H1("Todo"), taskForm(), delBtn, taskList);
	}

	private HorizontalLayout taskForm() {
		Binder<Task> binder = new BeanValidationBinder<>(Task.class);
		binder.bindInstanceFields(this);
		binder.setBean(task);

		name.focus();

		Button addBtn = new Button(VaadinIcon.PLUS.create(), click -> {
			if (binder.validate().isOk()) {
				taskService.save(task);
				taskList.addComponentAsFirst(new TaskLayout(task, taskService));
				task = new Task();
				binder.setBean(task);
			}
		});
		addBtn.addClickShortcut(Key.ENTER);
		addBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		HorizontalLayout layout = new HorizontalLayout(name, addBtn);
		layout.expand(name);
		layout.setWidth("50%");
		return layout;
	}
}
