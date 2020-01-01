package hr.petkovic.incomeexpense.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.DTO.PlannedAggDTO;
import hr.petkovic.incomeexpense.DTO.PlannedVsAchievedDTO;
import hr.petkovic.incomeexpense.service.PlanService;
import hr.petkovic.incomeexpense.service.TransactionService;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	private PlanService planService;
	@Autowired
	private TransactionService transService;

	public StatisticsController(PlanService planService, TransactionService transService) {
		this.planService = planService;
		this.transService = transService;
	}

	@GetMapping("/planned")
	public String getPlannedVsAchieved(Model model) {
		List<PlannedVsAchievedDTO> toShow = new ArrayList<>();
		List<PlannedAggDTO> aggs = planService.getAllPlansLevel0();
		for (PlannedAggDTO agg : aggs) {
			toShow.add(new PlannedVsAchievedDTO(agg, transService.findSumForTypeLvl0(agg)));
		}
		model.addAttribute("pvaGeneralDTOs", toShow);
		return "statistics/plansVsTransactions";
	}
}
