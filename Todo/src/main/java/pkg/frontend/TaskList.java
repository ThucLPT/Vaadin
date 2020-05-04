package pkg.frontend;

import java.util.List;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import pkg.backend.Task;
import pkg.backend.TaskService;

public class TaskList extends VerticalLayout {
	private static final long serialVersionUID = 1L;

	public TaskList(List<Task> tasks, TaskService taskService) {
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		tasks.forEach(task -> add(new TaskLayout(task, taskService)));
	}
}
