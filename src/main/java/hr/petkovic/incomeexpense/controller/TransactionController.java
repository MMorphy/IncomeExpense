package hr.petkovic.incomeexpense.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.petkovic.incomeexpense.DTO.FinancialTransactionDTO;
import hr.petkovic.incomeexpense.entity.Buyer;
import hr.petkovic.incomeexpense.entity.Company;
import hr.petkovic.incomeexpense.entity.Contract;
import hr.petkovic.incomeexpense.entity.Currency;
import hr.petkovic.incomeexpense.entity.FinancialTransaction;
import hr.petkovic.incomeexpense.entity.User;
import hr.petkovic.incomeexpense.service.BuyerService;
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
	@Autowired
	private BuyerService buyerService;

	public TransactionController(TransactionService transService, CompanyService companyService,
			CurrencyService currencyService, UserService userService, ContractService contractService,
			BuyerService buyerService) {
		this.transService = transService;
		this.companyService = companyService;
		this.currencyService = currencyService;
		this.userService = userService;
		this.contractService = contractService;
		this.buyerService = buyerService;
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

	@GetMapping("/contracts/{id}")
	public String getAllTransactionsFromContract(@PathVariable Long id, Model model) {
		model.addAttribute("transactions", transService.findTransactionsByContractId(id));
		return "transactions/transactions";
	}

	@GetMapping("/companies/{id}")
	public String getAllTransactionsFromCompany(@PathVariable Long id, Model model) {
		model.addAttribute("transactions", transService.findTransactionsByCompanyId(id));
		return "transactions/transactions";
	}

	@GetMapping("/add")
	public String getTransactionAdding(Model model) {
		model.addAttribute("addTrans", new FinancialTransactionDTO());
		model.addAttribute("companies", companyService.findAllCompanies());
		model.addAttribute("types", transService.findAllTransactionTypes());
		model.addAttribute("contracts", transService.findAllContracts());
		return "transactions/transactionsAdd";
	}

	@PostMapping("/add")
	public String addTransaction(FinancialTransactionDTO addTrans, Model model) {
		User currentUser = userService
				.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		FinancialTransaction trans = addTrans.getTrans();
		Currency dollars = currencyService.findCurrencyByNameCode("USD");
		if (dollars == null) {
			return "redirect:/trans/" + currentUser.getUsername();
		} else {
			addTrans.getTrans().setCurrency(dollars);
		}
		trans.setCreateDate(new Date());
		trans.setCreatedBy(currentUser);
		Company comp = addTrans.getCompany();
		Contract cont = addTrans.getContract();
		if (cont == null) {
			comp.addTransaction(trans);
		} else {
			cont.addTransaction(trans);
			Buyer buyer = buyerService.findBuyerByContractId(cont.getId());
			buyer.addContract(cont);
		}
		companyService.saveCompany(comp);
		model.addAttribute("transactions", transService.findTransactionsByUsername(currentUser.getUsername()));
		return "redirect:/trans/" + currentUser.getUsername();
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
		model.addAttribute("types", transService.findAllTransactionTypes());
		model.addAttribute("contracts", transService.findAllContracts());
		return "transactions/transactionsEdit";
	}

	@PostMapping("/edit/{id}")
	public String transactionEdit(@PathVariable("id") Long id, Model model, FinancialTransactionDTO editTrans) {
		editTrans.getTrans().setId(id);

		boolean companyChanged = false;
		boolean contractChanged = false;
		FinancialTransaction oldTrans = transService.findTransactionById(id);

		Currency dollars = currencyService.findCurrencyByNameCode("USD");
		if (dollars == null) {
			return "redirect:/trans/" + oldTrans.getCreatedBy().getUsername();
		} else {
			editTrans.getTrans().setCurrency(dollars);
		}
		FinancialTransaction newTrans = editTrans.getTrans();

		Company oldCompany = companyService.findCompanyByTransaction(oldTrans);
		if (oldCompany == null) {
			oldCompany = companyService.findCompanyByTransactionInBuyer(oldTrans);
		}
		Company newCompany = editTrans.getCompany();

		Contract oldContract = contractService.findContractByTransaction(oldTrans);
		Contract newContract = editTrans.getContract();

		boolean oldContractNull = false;
		if (oldContract == null) {
			oldContractNull = true;
		}
		boolean newContractNull = false;

		if (newContract == null) {
			newContractNull = true;
		}

		if (!oldCompany.equals(newCompany)) {
			companyChanged = true;
		}

		if ((oldContractNull && !newContractNull) || (!oldContract.equals(newContract))) {
			contractChanged = true;
		}

		// Actual saves
		if (companyChanged == true && contractChanged == true) {
			// oldCompany no contract -> newCompany contract
			if (oldContractNull == true && newContractNull == false) {
				oldCompany.removeTransaction(oldTrans);
				companyService.updateCompany(oldCompany.getId(), oldCompany);
				List<Buyer> newCompanyBuyers = (List<Buyer>) newCompany.getBuyers();
				for (Buyer b : newCompanyBuyers) {
					for (Contract c : b.getContracts()) {
						if (c.getId().equals(newContract.getId())) {
							c.addTransaction(newTrans);
						}
					}
				}
				companyService.updateCompany(newCompany.getId(), newCompany);
				// oldCompany contract-> newCompany no contract
			} else if (oldContractNull == false && newContractNull == true) {
				List<Buyer> oldCompanyBuyers = (List<Buyer>) oldCompany.getBuyers();
				for (Buyer b : oldCompanyBuyers) {
					for (Contract c : b.getContracts()) {
						c.removeTransaction(oldTrans);
					}
				}
				companyService.updateCompany(oldCompany.getId(), oldCompany);
				newCompany.addTransaction(newTrans);
			}
		} else if (companyChanged == false && contractChanged == true) {
			if (oldContractNull == true && newContractNull == false) {
				oldCompany.removeTransaction(oldTrans);
				newContract.addTransaction(newTrans);
				companyService.updateCompany(oldCompany.getId(), oldCompany);
				contractService.updateContract(newContract.getId(), newContract);
			} else if (oldContractNull == false && newContractNull == true) {
				oldContract.removeTransaction(oldTrans);
				newCompany.addTransaction(newTrans);
			}
		} else if (companyChanged == false && contractChanged == false) {
			if (oldContractNull == true) {
				oldCompany.removeTransaction(oldTrans);
				oldCompany.addTransaction(newTrans);
				companyService.updateCompany(oldCompany.getId(), oldCompany);
			} else {
				oldContract.removeTransaction(oldTrans);
				oldContract.addTransaction(newTrans);
				contractService.updateContract(oldContract.getId(), oldContract);
			}
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
