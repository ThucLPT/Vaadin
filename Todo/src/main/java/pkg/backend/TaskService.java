package pkg.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;

	public Task save(Task task) {
		return taskRepository.save(task);
	}

	public List<Task> findAll() {
		return taskRepository.findAll(Sort.by(Direction.DESC, "id"));
	}

	public void deleteByDone() {
		taskRepository.deleteByDone(true);
	}
}
