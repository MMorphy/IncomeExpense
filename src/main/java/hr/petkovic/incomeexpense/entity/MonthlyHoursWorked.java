package hr.petkovic.incomeexpense.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "hours_worked")
public class MonthlyHoursWorked {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Float actualHours;

	@Temporal(TemporalType.DATE)
	private Date yearAndMonth;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getActualHours() {
		return actualHours;
	}

	public void setActualHours(Float actualHours) {
		this.actualHours = actualHours;
	}

	public Date getYearAndMonth() {
		return yearAndMonth;
	}

	public void setYearAndMonth(Date yearAndMonth) {
		this.yearAndMonth = yearAndMonth;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public int hashCode() {
		return 101;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof MonthlyHoursWorked))
			return false;
		return id != null && id.equals(((MonthlyHoursWorked) o).getId());
	}

}
