package hr.petkovic.incomeexpense.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.entity.Employee;
import hr.petkovic.incomeexpense.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeesController {

	@Autowired
	private EmployeeService eService;

	@GetMapping("/home")
	public String home() {
		return "employees/employeesHome";
	}

	@GetMapping("/active")
	public String getAllActiveEmployees(Model model) {
		model.addAttribute("employees", eService.findAllEmployeesByActivity(true));
		model.addAttribute("heading", "Currently employeed");
		return "employees/employees";
	}

	@GetMapping("/fired")
	public String getAllFiredEmployees(Model model) {
		model.addAttribute("employees", eService.findAllEmployeesByActivity(false));
		model.addAttribute("heading", "Fired Employees");
		return "employees/employeesFired";
	}

	@GetMapping("/all")
	public String getAllEmployees(Model model) {
		model.addAttribute("employees", eService.findAllEmployees());
		model.addAttribute("heading", "All Employees");
		return "employees/employees";
	}

	@GetMapping("/add")
	public String getEmployeeAdding(Model model) {
		model.addAttribute("addEmployee", new Employee());
		return "employees/employeesAdd";
	}

	@PostMapping("/add")
	public String addEmployee(Model model, Employee addEmployee) {
		addEmployee.setActive(true);
		eService.saveEmployee(addEmployee);
		return "redirect:/employees/home";
	}

	@GetMapping("/edit/{id}")
	public String getEmployeeEditing(@PathVariable("id") Long id, Model model) {
		Employee e = eService.findEmployeeById(id);
		model.addAttribute("editEmployee", e);
		return "employees/employeesEdit";
	}

	@PostMapping("/edit/{id}")
	public String editEmployee(@PathVariable("id") Long id, Employee editEmployee) {
		eService.updateEmployee(id, editEmployee);
		return "employees/employeesHome";
	}

	@GetMapping("/salaries/{id}")
	public String getEmployeeSalaries(@PathVariable("id") Long id, Model model) {
		model.addAttribute("transactions", eService.findEmployeeById(id).getSalaries());
		model.addAttribute("employeeId", id);
		return "employees/employeeSalaries";
	}

	@GetMapping()
	// TODO napravi payment modal
	@PostMapping("/pay/{id}")
	public String getEmployeePayment(@PathVariable("id") Long id, Model model) {
		return "employees/employeesHome";
	}

	@PostMapping("/fire/{id}")
	public String fireEmployee(@PathVariable("id") Long id) {
		eService.fireEmployeeById(id);
		return home();
	}
}
