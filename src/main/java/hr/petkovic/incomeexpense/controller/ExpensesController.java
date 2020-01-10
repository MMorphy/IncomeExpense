package hr.petkovic.incomeexpense.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.entity.Company;
import hr.petkovic.incomeexpense.entity.FinancialTransaction;
import hr.petkovic.incomeexpense.entity.User;
import hr.petkovic.incomeexpense.service.CompanyService;
import hr.petkovic.incomeexpense.service.ContractService;
import hr.petkovic.incomeexpense.service.CurrencyService;
import hr.petkovic.incomeexpense.service.TransactionService;
import hr.petkovic.incomeexpense.service.TransactionTypeService;
import hr.petkovic.incomeexpense.service.UserService;

@Controller
@RequestMapping("/expenses")
public class ExpensesController {

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

	public ExpensesController(TransactionService transService, CompanyService compService, ContractService contrService,
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
		model.addAttribute("types", typeService.findAllExpensesTransactionTypes());
		return "transactions/expenses";
	}

	@PostMapping("/add")
	public String addTransaction(FinancialTransaction addTrans, Model model) {
		User currentUser = userService
				.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//		Currency dollars = currService.findCurrencyByNameCode("USD");
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

}
