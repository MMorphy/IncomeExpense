package hr.petkovic.incomeexpense.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String position;

	private Float salary;

	private Float bonus;

	private Float expectedHours;

	private Boolean active;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MonthlyHoursWorked> monthlyHoursWorked;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "employee_id")
	private List<FinancialTransaction> salaries;

	public Employee() {
		this.monthlyHoursWorked = new ArrayList<>();
		this.salaries = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}

	public Float getBonus() {
		return bonus;
	}

	public void setBonus(Float bonus) {
		this.bonus = bonus;
	}

	public Float getExpectedHours() {
		return expectedHours;
	}

	public void setExpectedHours(Float expectedHours) {
		this.expectedHours = expectedHours;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public List<MonthlyHoursWorked> getMonthlyHoursWorked() {
		return monthlyHoursWorked;
	}

	public void setMonthlyHoursWorked(List<MonthlyHoursWorked> monthlyHoursWorked) {
		this.monthlyHoursWorked = monthlyHoursWorked;
	}

	public List<FinancialTransaction> getSalaries() {
		return salaries;
	}

	public void setSalaries(List<FinancialTransaction> salaries) {
		this.salaries = salaries;
	}

	public void addHours(MonthlyHoursWorked hours) {
		monthlyHoursWorked.add(hours);
		hours.setEmployee(this);
	}

	public void removeHours(MonthlyHoursWorked hours) {
		monthlyHoursWorked.remove(hours);
		if (hours.getEmployee().equals(this)) {
			hours.setEmployee(null);
		}
	}
}
