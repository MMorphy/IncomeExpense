package hr.petkovic.incomeexpense.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.entity.Company;
import hr.petkovic.incomeexpense.entity.Currency;
import hr.petkovic.incomeexpense.entity.FinancialTransaction;
import hr.petkovic.incomeexpense.entity.User;
import hr.petkovic.incomeexpense.service.CompanyService;
import hr.petkovic.incomeexpense.service.ContractService;
import hr.petkovic.incomeexpense.service.CurrencyService;
import hr.petkovic.incomeexpense.service.TransactionService;
import hr.petkovic.incomeexpense.service.TransactionTypeService;
import hr.petkovic.incomeexpense.service.UserService;

@Controller
@RequestMapping("/income")
public class IncomeController {

	@Autowired
	private TransactionService transService;
	@Autowired
	private CompanyService compService;
	@Autowired
	private ContractService contrService;
	@Autowired
	private CurrencyService currService;
	@Autowired
	private TransactionTypeService typeService;
	@Autowired
	private UserService userService;

	public IncomeController(TransactionService transService, CompanyService compService, ContractService contrService,
			CurrencyService currService, TransactionTypeService typeService, UserService userService) {
		this.transService = transService;
		this.compService = compService;
		this.contrService = contrService;
		this.currService = currService;
		this.typeService = typeService;
		this.userService = userService;
	}

	@GetMapping("/add")
	public String getTransactionAdding(Model model) {
		model.addAttribute("addTrans", new FinancialTransaction());
		model.addAttribute("types", typeService.findAllIncomeTransactionTypes());
		return "transactions/income";
	}

	@PostMapping("/add")
	public String addTransaction(FinancialTransaction addTrans, Model model) {
		User currentUser = userService
				.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Currency dollars = currService.findCurrencyByNameCode("USD");
//		if (dollars == null) {
//			return "redirect:/";
//		} else {
//			addTrans.setCurrency(dollars);
//		}
		Company defaultCompany = compService.findDefaultCompany();
		if (defaultCompany == null) {
			return "redirect:/";
		} else {
		}
		addTrans.setCreateDate(new Date());
		addTrans.setCreatedBy(currentUser);
		defaultCompany.addTransaction(addTrans);
		compService.saveCompany(defaultCompany);
		return "redirect:/";
	}

	//TODO add income trans to contract
	@GetMapping("/add/contract/{id}")
	public String getAddIncomeToContract(@PathVariable("id") Long id, Model model) {
		return "redirect:/contracts";
	}
}
