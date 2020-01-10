package hr.petkovic.incomeexpense.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.entity.Task;
import hr.petkovic.incomeexpense.entity.User;
import hr.petkovic.incomeexpense.service.TaskService;
import hr.petkovic.incomeexpense.service.UserService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private UserService userService;

	private static SimpleDateFormat format;

	@PostConstruct
	public void init() {
		format = new SimpleDateFormat("yyyy-MM-dd");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	public TaskController(TaskService taskService, UserService userService) {
		this.taskService = taskService;
		this.userService = userService;
	}

	@GetMapping()
	public String getAllTasks(Model model) {
		model.addAttribute("todayUnfinishedTasks", taskService.findAllTasksForToday(false));
		model.addAttribute("overdueTasks", taskService.findOverdueTasks());
		model.addAttribute("allTasks", taskService.findAllTasks());
		return "tasks/tasks";
	}

	@GetMapping("/user/{username}")
	public String getMyTasks(@PathVariable("username") String username, Model model) {
		model.addAttribute("todayUnfinishedTasks", taskService.findAllTasksUserAndToday(username, false));
		model.addAttribute("overdueTasks", taskService.findOverDueTasksForUsername(username));
		model.addAttribute("allTasks", taskService.findAllTasksForUser(username));
		return "tasks/myTasks";
	}

	@GetMapping("/add")
	public String getTaskAdding(Model model) {
		model.addAttribute("addTask", new Task());
		model.addAttribute("users", userService.findAllEnabledUsers());
		return "tasks/tasksAdd";
	}

	@PostMapping("/add")
	public String addTask(Task addTask) {
		User currentUser = userService
				.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		addTask.setFromUser(currentUser);
		taskService.saveTask(addTask);
		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String getTaskEditing(@PathVariable("id") Long id, Model model) {
		Task t = taskService.findTaskById(id);
		model.addAttribute("editTask", t);
		model.addAttribute("users", userService.findAllEnabledUsers());
		return "tasks/tasksEdit";
	}

	@PostMapping("/edit/{id}")
	public String editTask(@PathVariable("id") Long id, Model model, Task editTask) {
		Task oldTask = taskService.findTaskById(id);
		oldTask.setDescription(editTask.getDescription());
		oldTask.setDueDate(editTask.getDueDate());
		oldTask.setToUser(editTask.getToUser());
		taskService.updateTask(id, oldTask);
		model.addAttribute("todayUnfinishedTasks", taskService.findAllTasksForToday(false));
		model.addAttribute("overdueTasks", taskService.findOverdueTasks());
		model.addAttribute("allTasks", taskService.findAllTasks());
		return "redirect:/tasks";
	}

	@PostMapping("/delete/{id}")
	public String deteleTaks(@PathVariable("id") Long id, Model model) {
		taskService.deleteTask(id);
		model.addAttribute("todayUnfinishedTasks", taskService.findAllTasksForToday(false));
		model.addAttribute("overdueTasks", taskService.findOverdueTasks());
		model.addAttribute("allTasks", taskService.findAllTasks());
		return "redirect:/tasks";
	}
}
