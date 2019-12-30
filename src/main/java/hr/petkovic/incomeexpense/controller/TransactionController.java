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

//	private static Logger logger = LoggerFactory.getLogger(TransactionController.class);

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

	@GetMapping()
	public String getAllTransactions(Model model) {
		model.addAttribute("transactions", transService.findAllTransactions());
		return "transactions/transactions";
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

	@GetMapping("/edit/{id}")
	public String getTransactionEdit(@PathVariable("id") Long id, Model model) {
		FinancialTransaction trans = transService.findTransactionById(id);
		FinancialTransactionDTO editTrans = new FinancialTransactionDTO();

		editTrans.setTrans(trans);
		editTrans.setCompany(companyService.findCompanyByTransaction(trans));
		editTrans.setContract(contractService.findContractByTransaction(trans));

		model.addAttribute("editTrans", editTrans);
		model.addAttribute("companies", companyService.findAllCompanies());
		model.addAttribute("currencies", currencyService.findAllCurrencies());
		model.addAttribute("types", transService.findAllTransactionTypes());
		model.addAttribute("contracts", transService.findAllContracts());
		return "transactions/transactionsEdit";
	}

	@PostMapping("/edit/{id}")
	public String transactionEdit(@PathVariable("id") Long id, Model model, FinancialTransactionDTO editTrans) {
		FinancialTransaction oldTrans = transService.findTransactionById(id);
		boolean needToDelete = false;

		Company newCompany = editTrans.getCompany();
		Company oldCompany = companyService.findCompanyByTransaction(oldTrans);
		if (!oldCompany.equals(newCompany) || !oldCompany.getId().equals(newCompany.getId())) {
			oldCompany.removeTransaction(oldTrans);
			companyService.saveCompany(oldCompany);

			editTrans.getTrans().setCreatedBy(oldTrans.getCreatedBy());
			newCompany.addTransaction(editTrans.getTrans());
			companyService.saveCompany(newCompany);
			needToDelete = true;
		}
		Contract newContract = editTrans.getContract();
		Contract oldContract = contractService.findContractByTransaction(oldTrans);
		if (!oldContract.equals(newContract) || !oldContract.getId().equals(newContract.getId())) {
			oldContract.removeTransaction(oldTrans);
			contractService.saveContract(oldContract);

			editTrans.getTrans().setCreatedBy(oldTrans.getCreatedBy());
			newContract.addTransaction(editTrans.getTrans());
			contractService.saveContract(newContract);
			needToDelete = true;
		}
		if (needToDelete) {
			transService.deleteTransactionById(oldTrans.getId());
		}
		model.addAttribute("transactions",
				transService.findTransactionsByUsername(oldTrans.getCreatedBy().getUsername()));
		return "redirect:/trans/" + oldTrans.getCreatedBy().getUsername();
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
		model.addAttribute("transactions", transService.findTransactionsByUsername(username));
		return "redirect:/trans/" + username;
	}
}
