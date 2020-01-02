package hr.petkovic.incomeexpense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.DTO.PlannedAggDTO;
import hr.petkovic.incomeexpense.entity.Plan;
import hr.petkovic.incomeexpense.repository.PlanRepository;

@Service
public class PlanService {

	@Autowired
	private PlanRepository planRepo;

	public PlanService(PlanRepository planRepo) {
		this.planRepo = planRepo;
	}

	public Plan findPlanById(Long id) {
		try {
			return planRepo.findById(id).get();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Plan> findAllPlans() {
		return planRepo.findAll();
	}

	public Plan savePlan(Plan plan) {
		return planRepo.save(plan);
	}

	public Plan updatePlan(Long id, Plan plan) {
		Optional<Plan> optPlan = planRepo.findById(id);
		if (optPlan.isPresent()) {
			Plan p = optPlan.get();
			p.setAmount(plan.getAmount());
			p.setFromDate(plan.getFromDate());
			p.setToDate(plan.getToDate());
			p.setType(plan.getType());
			return planRepo.save(p);
		} else {
			return planRepo.save(plan);
		}
	}

	public void deletePlanById(Long id) {
		planRepo.deleteById(id);
	}

	public List<PlannedAggDTO> getAllPlansLevel0() {
		return planRepo.sumAmountByTransactionDateLevelZero();
	}

	public List<PlannedAggDTO> getAllPlansLevel1() {
		return planRepo.sumAmountByTransactionDateLevelOne();
	}

	public List<PlannedAggDTO> getAllPlansLevel2() {
		return planRepo.sumAmountByTransactionDateLevelTwo();
	}
}
