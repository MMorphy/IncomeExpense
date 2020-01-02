package hr.petkovic.incomeexpense.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

		List<PlannedVsAchievedDTO> toShowTwo = new ArrayList<>();
		aggs = planService.getAllPlansLevel2();
		for (PlannedAggDTO agg : aggs) {
			toShowTwo.add(new PlannedVsAchievedDTO(agg, transService.findSumForTypeLvl2(agg)));
		}
		model.addAttribute("pvaLvl2DTOs", toShowTwo);
		return "statistics/plansVsTransactions";
	}

	@GetMapping("/time")
	public String getTimeStats(Model model) {
		List<TimeAggDTO> incomeDtos = transService.findIncomeTransactionsGroupedByYearAndMonth();
		Collections.sort(incomeDtos, new YearMonthComparator());
		model.addAttribute("incomeYearMonth", incomeDtos);

		List<TimeAggDTO> expensesDtos = transService.findExpensesTransactionsGroupedByYearAndMonth();
		Collections.sort(expensesDtos, new YearMonthComparator());
		model.addAttribute("expensesYearMonth", expensesDtos);

		List<TimeAggDTO> incomeYear = transService.findAllIncomeTransactionsGroupedByYear();
		Collections.sort(incomeYear, new YearComparator());
		model.addAttribute("incomeYear", incomeYear);

		List<TimeAggDTO> expensesYear = transService.findAllExpensesTransactionsGroupedByYear();
		Collections.sort(expensesYear, new YearComparator());
		model.addAttribute("expensesYear", expensesYear);
		return "statistics/time";
	}

	public class YearMonthComparator implements Comparator<TimeAggDTO> {

		@Override
		public int compare(TimeAggDTO o1, TimeAggDTO o2) {
			int sComp = o1.getYear().compareTo(o2.getYear());
			if (sComp != 0) {
				return sComp;
			}
			return o1.getMonth().compareTo(o2.getMonth());
		}
	}

	public class YearComparator implements Comparator<TimeAggDTO> {

		@Override
		public int compare(TimeAggDTO o1, TimeAggDTO o2) {
			return o1.getYear().compareTo(o2.getYear());
		}
	}
}
