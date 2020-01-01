package hr.petkovic.incomeexpense.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.DTO.PlanDTO;
import hr.petkovic.incomeexpense.entity.Plan;
import hr.petkovic.incomeexpense.service.PlanService;
import hr.petkovic.incomeexpense.service.TransactionTypeService;

@Controller
@RequestMapping("/plans")
public class PlanController {

	@Autowired
	private PlanService planService;
	@Autowired
	private TransactionTypeService typeService;

	private static SimpleDateFormat format;

	@PostConstruct
	public void init() {
		format = new SimpleDateFormat("yyyy-MM-dd");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	public PlanController(PlanService planService, TransactionTypeService typeService) {
		this.planService = planService;
		this.typeService = typeService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@GetMapping()
	public String getAllBuyers(Model model) {
		model.addAttribute("plans", planService.findAllPlans());
		return "plans/plans";
	}

	@GetMapping("/add")
	public String getPlanAdding(Model model) {
		model.addAttribute("addPlan", new PlanDTO());
		model.addAttribute("types", typeService.findAllTransactionTypes());
		return "plans/plansAdd";
	}

	@PostMapping("/add")
	public String addPlan(PlanDTO addPlan) {
		Plan plan = addPlan.getPlan();
		try {
			plan.setToDate(format.parse(addPlan.getToDate()));
			plan.setFromDate(format.parse(addPlan.getFromDate()));
		} catch (Exception e) {
			return "redirect:/plans";
		}
		planService.savePlan(plan);
		return "redirect:/plans";
	}

	@GetMapping("/edit/{id}")
	public String getPlanEditing(@PathVariable("id") Long id, Model model) {
		Plan plan = planService.findPlanById(id);
		PlanDTO dto = new PlanDTO();
		dto.setPlan(plan);
		dto.setFromDate(plan.getFromDate().toString());
		dto.setToDate(plan.getToDate().toString());
		model.addAttribute("editPlan", dto);
		model.addAttribute("types", typeService.findAllTransactionTypes());
		return "plans/plansEdit";

	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@PostMapping("/edit/{id}")
	public String editPlan(@PathVariable("id") Long id, PlanDTO editPlan, Model model) {
		Plan plan = editPlan.getPlan();
		try {
			plan.setToDate(format.parse(editPlan.getToDate()));
			plan.setFromDate(format.parse(editPlan.getFromDate()));
		} catch (Exception e) {
			return "redirect:/plans";
		}
		planService.updatePlan(id, plan);
		model.addAttribute("plans", planService.findAllPlans());
		return "redirect:/plans";
	}

	@PostMapping("/delete/{id}")
	public String detelePlan(@PathVariable("id") Long id, Model model) {
		planService.deletePlanById(id);
		model.addAttribute("plans", planService.findAllPlans());
		return "redirect:/plans";
	}
}
