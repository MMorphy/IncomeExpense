package hr.petkovic.incomeexpense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hr.petkovic.incomeexpense.DTO.PlannedAggDTO;
import hr.petkovic.incomeexpense.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long>{

	@Query("SELECT "
			+ "new hr.petkovic.incomeexpense.DTO.PlannedAggDTO(p.fromDate, p.toDate, SUM(amount), p.type) "
		+ "FROM "
			+ "Plan p "
		+ "GROUP BY "
			+ "p.fromDate, p.toDate, p.type.name")
	public List<PlannedAggDTO> sumAmountByTransactionDateLevelOne();
}
