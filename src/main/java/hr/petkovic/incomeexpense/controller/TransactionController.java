package hr.petkovic.incomeexpense.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.entity.FinancialTransaction;
import hr.petkovic.incomeexpense.entity.User;
import hr.petkovic.incomeexpense.service.CompanyService;
import hr.petkovic.incomeexpense.service.CurrencyService;
import hr.petkovic.incomeexpense.service.TransactionService;
import hr.petkovic.incomeexpense.service.UserService;

@Controller
@RequestMapping("/trans")
public class TransactionController {

	private static Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	private TransactionService transService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private UserService userService;

	public TransactionController(TransactionService transService, CompanyService companyService,
			CurrencyService currencyService, UserService userService) {
		this.transService = transService;
		this.companyService = companyService;
		this.currencyService = currencyService;
		this.userService = userService;
	}

	@GetMapping("{username}")
	public String getMyTransactions(@PathVariable String username, Model model) {
		model.addAttribute("transactions", transService.findTransactionsByUsername(username));
		return "transactions/transactions";
	}

	@GetMapping("/add")
	public String getTransactionAdding(Model model, FinancialTransactionDTO addTrans) {
		model.addAttribute("companies", companyService.findAllCompanies());
		model.addAttribute("currencies", currencyService.findAllCurrencies());
		model.addAttribute("types", transService.findAllTransactionTypes());
		return "transactions/transactionsAdd";
	}

	@PostMapping("/add")
	public String addTransaction(FinancialTransaction addTrans) {
		User currentUser = userService
				.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		addTrans.setCreatedBy(currentUser);
		transService.saveTransaction(addTrans);
		return "redirect:/";
	}
}
