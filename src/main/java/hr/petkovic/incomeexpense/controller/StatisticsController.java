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
import hr.petkovic.incomeexpense.DTO.TimeAggDTO;
import hr.petkovic.incomeexpense.service.PlanService;
import hr.petkovic.incomeexpense.service.StatisticsService;
import hr.petkovic.incomeexpense.service.TransactionService;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	private PlanService planService;
	@Autowired
	private TransactionService transService;
	@Autowired
	private StatisticsService statsService;

	public StatisticsController(PlanService planService, TransactionService transService,
			StatisticsService statsService) {
		this.planService = planService;
		this.transService = transService;
		this.statsService = statsService;
	}

	@GetMapping("/planned")
	public String getPlannedVsAchieved(Model model) {
		List<PlannedVsAchievedDTO> toShowZero = new ArrayList<>();
		List<PlannedAggDTO> aggs = planService.getAllPlansLevel0();
		for (PlannedAggDTO agg : aggs) {
			toShowZero.add(new PlannedVsAchievedDTO(agg, transService.findSumForTypeLvl0(agg)));
		}
		model.addAttribute("pvaLvl0DTOs", toShowZero);

		List<PlannedVsAchievedDTO> toShowOne = new ArrayList<>();
		aggs = planService.getAllPlansLevel1();
		for (PlannedAggDTO agg : aggs) {
			toShowOne.add(new PlannedVsAchievedDTO(agg, transService.findSumForTypeLvl1(agg)));
		}
		model.addAttribute("pvaLvl1DTOs", toShowOne);

//		List<PlannedVsAchievedDTO> toShowTwo = new ArrayList<>();
//		aggs = planService.getAllPlansLevel2();
//		for (PlannedAggDTO agg : aggs) {
//			toShowTwo.add(new PlannedVsAchievedDTO(agg, transService.findSumForTypeLvl2(agg)));
//		}
//		model.addAttribute("pvaLvl2DTOs", toShowTwo);
		return "statistics/plansVsTransactions";
	}

	@GetMapping("/time")
	public String getTimeStats(Model model) {
		List<TimeAggDTO> incomeDtos = transService.findIncomeTransactionsGroupedByYearAndMonth();
		model.addAttribute("incomeYearMonth", statsService.getDifferencesAndSort(incomeDtos, false));

		List<TimeAggDTO> expensesDtos = transService.findExpensesTransactionsGroupedByYearAndMonth();
		model.addAttribute("expensesYearMonth", statsService.getDifferencesAndSort(expensesDtos, false));

		List<TimeAggDTO> incomeYear = transService.findAllIncomeTransactionsGroupedByYear();
		model.addAttribute("incomeYear", statsService.getDifferencesAndSort(incomeYear, true));

		List<TimeAggDTO> expensesYear = transService.findAllExpensesTransactionsGroupedByYear();
		model.addAttribute("expensesYear", statsService.getDifferencesAndSort(expensesYear, true));
		return "statistics/time";
	}
}
