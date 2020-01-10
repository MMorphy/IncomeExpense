package hr.petkovic.incomeexpense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.entity.Task;
import hr.petkovic.incomeexpense.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepo;

	public TaskService(TaskRepository taskRepo) {
		this.taskRepo = taskRepo;
	}

	public Task findTaskById(Long id) {
		Optional<Task> opt = taskRepo.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else
			return null;
	}

	public List<Task> findAllTasks() {
		return taskRepo.findAll();
	}

	public List<Task> findAllFinished(boolean finished) {
		return taskRepo.findByFinished(finished);
	}

	public List<Task> findAllTasksForToday(boolean finished) {
		return taskRepo.findByTodaysDateAndFinished(finished);
	}

	public List<Task> findAllTasksUserAndToday(String username, boolean finished) {
		return taskRepo.findByTodaysDateAndToUsername(username, finished);
	}

	public List<Task> findAllTasksForUser(String username) {
		return taskRepo.findByToUser_Username(username);
	}

	public List<Task> findOverdueTasks() {
		return taskRepo.findOverdue();
	}

	public List<Task> findOverDueTasksForUsername(String username) {
		return taskRepo.findOverdueForToUsername(username);
	}

	public boolean finishTask(Long id) {
		Optional<Task> optTask = taskRepo.findById(id);
		if (optTask.isPresent()) {
			Task t = optTask.get();
			t.setFinished(true);
			taskRepo.save(t);
			return true;
		} else
			return false;
	}

	public Task saveTask(Task t) {
		return taskRepo.save(t);
	}

	public Task updateTask(Long id, Task task) {
		Optional<Task> optTask = taskRepo.findById(id);
		if (optTask.isPresent()) {
			Task t = optTask.get();
			t.setDescription(task.getDescription());
			t.setDueDate(task.getDueDate());
			t.setFinished(task.isFinished());
			t.setFromUser(task.getFromUser());
			t.setToUser(task.getToUser());
			return taskRepo.save(t);
		} else
			return taskRepo.save(task);
	}

	public void deleteTask(Long id) {
		taskRepo.deleteById(id);
	}
}
