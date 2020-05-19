package hr.petkovic.incomeexpense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.MonthlyHoursWorked;

public interface MonthlyHoursWorkedRepository extends JpaRepository<MonthlyHoursWorked, Long> {

	public List<MonthlyHoursWorked> findAllByEmployee_Id(Long id);

}
