package hr.petkovic.incomeexpense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.entity.Employee;
import hr.petkovic.incomeexpense.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository eRepo;

	public Employee findEmployeeById(Long id) {
		try {
			return eRepo.findById(id).get();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public List<Employee> findAllEmployees() {
		return eRepo.findAll();
	}

	public List<Employee> findAllEmployeesByActivity(Boolean active) {
		return eRepo.findByActive(active);
	}

	public List<Employee> findAllEmployeesWithPosition(String pos) {
		return eRepo.findByPosition(pos);
	}

	public Employee saveEmployee(Employee e) {
		return eRepo.save(e);
	}

	public Employee updateEmployee(Long id, Employee employee) {
		Optional<Employee> optE = eRepo.findById(id);
		if (optE.isPresent()) {
			Employee e = optE.get();
			e.setName(employee.getName());
			e.setExpectedHours(employee.getExpectedHours());
			e.setBonus(employee.getBonus());
			e.setSalary(employee.getSalary());
			e.setPosition(employee.getPosition());
			return saveEmployee(e);
		} else {
			return saveEmployee(employee);
		}
	}

	public boolean fireEmployeeById(Long id) {
		Optional<Employee> optE = eRepo.findById(id);
		if (optE.isPresent()) {
			Employee e = optE.get();
			e.setActive(false);
			saveEmployee(e);
			return true;
		} else
			return false;
	}
}
