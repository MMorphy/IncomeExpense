package hr.petkovic.incomeexpense.controller;

import java.util.Date;

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

import hr.petkovic.incomeexpense.DTO.FinancialTransactionDTO;
import hr.petkovic.incomeexpense.entity.Company;
import hr.petkovic.incomeexpense.entity.Contract;
import hr.petkovic.incomeexpense.entity.FinancialTransaction;
import hr.petkovic.incomeexpense.entity.User;
import hr.petkovic.incomeexpense.service.CompanyService;
import hr.petkovic.incomeexpense.service.ContractService;
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
	@Autowired
	private ContractService contractService;

	public TransactionController(TransactionService transService, CompanyService companyService,
			CurrencyService currencyService, UserService userService, ContractService contractService) {
		this.transService = transService;
		this.companyService = companyService;
		this.currencyService = currencyService;
		this.userService = userService;
		this.contractService = contractService;
	}

	@GetMapping("{username}")
	public String getMyTransactions(@PathVariable String username, Model model) {
		model.addAttribute("transactions", transService.findTransactionsByUsername(username));
		return "transactions/transactions";
	}

	@GetMapping("/add")
	public String getTransactionAdding(Model model) {
		model.addAttribute("addTrans", new FinancialTransactionDTO());
		model.addAttribute("companies", companyService.findAllCompanies());
		model.addAttribute("currencies", currencyService.findAllCurrencies());
		model.addAttribute("types", transService.findAllTransactionTypes());
		model.addAttribute("contracts", transService.findAllContracts());
		return "transactions/transactionsAdd";
	}

	@PostMapping("/add")
	public String addTransaction(FinancialTransactionDTO addTrans) {
		User currentUser = userService
				.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		FinancialTransaction trans = addTrans.getTrans();
		trans.setCreateDate(new Date());
		trans.setCreatedBy(currentUser);
		transService.saveTransaction(trans);

		Company comp = addTrans.getCompany();
		comp.addTransaction(trans);
		companyService.saveCompany(comp);
		if (addTrans.getContract() != null) {
			Contract cont = addTrans.getContract();
			cont.addTransaction(trans);
			contractService.saveContract(cont);
		}

		return "redirect:/";
	}

	@PostMapping("/delete/{id}")
	public String deleteTransaction(@PathVariable("id") Long id, Model model) {
		FinancialTransaction trans = transService.findTransactionById(id);
		if (trans != null) {
			Company comp = companyService.findCompanyByTransaction(trans);
			if (comp != null) {
				comp.removeTransaction(trans);
			}
			Contract contr = contractService.findContractByTransaction(trans);
			if (contr != null) {
				contr.removeTransaction(trans);
			}
		}
		transService.deleteTransactionById(id);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("transactions", transService
				.findTransactionsByUsername(username));
		return "redirect:/trans/" + username;
	}
}