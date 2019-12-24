package hr.petkovic.incomeexpense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long>{

}
