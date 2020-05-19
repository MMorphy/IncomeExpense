package hr.petkovic.incomeexpense.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.entity.Employee;
import hr.petkovic.incomeexpense.entity.MonthlyHoursWorked;
import hr.petkovic.incomeexpense.service.EmployeeService;
import hr.petkovic.incomeexpense.service.HoursService;

@Controller
@RequestMapping("/hours")
public class HoursController {

	@Autowired
	private HoursService hService;
	@Autowired
	private EmployeeService eService;

	private static SimpleDateFormat format;

	@PostConstruct
	public void init() {
		format = new SimpleDateFormat("yyyy-MM");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@GetMapping()
	public String getAllUserHours(Model model) {
		model.addAttribute("hours", hService.findAllHours());
		return "hours/hours";
	}

	@GetMapping("/user/{id}")
	public String getUserHours(@PathVariable("id") Long id, Model model) {
		model.addAttribute("userId", id);
		model.addAttribute("hours", hService.findAllHoursForEmployeeId(id));
		return "hours/hours";
	}

	@GetMapping("/add/{id}")
	public String getAddingHoursForUser(@PathVariable("id") Long id, Model model) {
		model.addAttribute("addHours", new MonthlyHoursWorked());
		model.addAttribute("employeeId", id);
		return "hours/hoursAddUser";
	}

	@PostMapping("/add/{id}")
	public String getAddingHoursForUser(@PathVariable("id") Long id, MonthlyHoursWorked addHours) {
		Employee e = eService.findEmployeeById(id);
		e.addHours(addHours);
		eService.saveEmployee(e);
		return "employees/employeesHome";
	}

}
