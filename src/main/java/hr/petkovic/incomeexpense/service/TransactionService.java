package hr.petkovic.incomeexpense.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.DTO.PlannedAggDTO;
import hr.petkovic.incomeexpense.DTO.TimeAggDTO;
import hr.petkovic.incomeexpense.entity.Contract;
import hr.petkovic.incomeexpense.entity.FinancialTransaction;
import hr.petkovic.incomeexpense.entity.TransactionType;
import hr.petkovic.incomeexpense.repository.CompanyRepository;
import hr.petkovic.incomeexpense.repository.ContractRepository;
import hr.petkovic.incomeexpense.repository.FinancialTransactionRepository;
import hr.petkovic.incomeexpense.repository.TransactionTypeRepository;

@Service
public class TransactionService {

	// TODO refactor this so that you use other services not repositories
	@Autowired
	private FinancialTransactionRepository transRepo;

	@Autowired
	private CompanyRepository companyRepo;

	@Autowired
	private TransactionTypeRepository typeRepo;

	@Autowired
	private ContractRepository contractRepo;

	@Autowired
	private ContractService contractService;

	@Autowired
	private CompanyService companyService;

	public TransactionService(FinancialTransactionRepository transRepo, CompanyRepository companyRepo,
			TransactionTypeRepository typeRepo, ContractRepository contractRepo, ContractService contractService,
			CompanyService companyService) {
		this.transRepo = transRepo;
		this.companyRepo = companyRepo;
		this.typeRepo = typeRepo;
		this.contractRepo = contractRepo;
		this.contractService = contractService;
		this.companyService = companyService;
	}

	public FinancialTransaction findTransactionById(Long id) {
		Optional<FinancialTransaction> optTrans = transRepo.findById(id);
		if (optTrans.isPresent()) {
			return optTrans.get();
		} else
			return null;
	}

	public List<FinancialTransaction> findAllTransactions() {
		return transRepo.findAll();
	}

	public List<FinancialTransaction> findTransactionsByCompanyName(String name) {
		List<FinancialTransaction> list = new ArrayList<>();
		try {
			list = (ArrayList<FinancialTransaction>) companyRepo.findByName(name).get().getTransactions();
		} catch (NoSuchElementException ex) {
			return list;
		}
		return list;
	}

	public List<FinancialTransaction> findTransactionsByContractId(Long id) {
		return (List<FinancialTransaction>) contractService.findContractById(id).getTransactions();
	}

	public List<FinancialTransaction> findTransactionsByCompanyId(Long id) {
		return (List<FinancialTransaction>) companyService.findCompanyById(id).getTransactions();
	}

	public List<FinancialTransaction> findTransactionsByUsername(String username) {
		List<FinancialTransaction> list = new ArrayList<>();
		try {
			list = (ArrayList<FinancialTransaction>) transRepo.findByCreatedBy_Username(username);
		} catch (NoSuchElementException ex) {
			return list;
		}
		return list;
	}

	public List<TransactionType> findAllTransactionTypes() {
		return typeRepo.findAll();
	}

	public FinancialTransaction saveTransaction(FinancialTransaction transaction) {
		return transRepo.save(transaction);
	}

	public FinancialTransaction updateTransaction(Long id, FinancialTransaction transaction) {
		Optional<FinancialTransaction> optTrans = transRepo.findById(id);
		if (optTrans.isPresent()) {
			FinancialTransaction trans = optTrans.get();
			trans.setAmount(transaction.getAmount());
//			trans.setCurrency(transaction.getCurrency());
			trans.setDescription(transaction.getDescription());
			trans.setType(transaction.getType());
			return transRepo.save(trans);
		} else {
			return transRepo.save(transaction);
		}
	}

	public List<Contract> findAllContracts() {
		return contractRepo.findAll();
	}

	public void deleteTransactionById(Long id) {
		this.transRepo.deleteById(id);
	}

	public Double findSumForTypeLvl0(PlannedAggDTO agg) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date toDate = agg.getToDate();
		Date fromDate = agg.getFromDate();
		String typeName = agg.getType().getName();
		Double sum = transRepo.findAllTransactionsSumByTypeLvl0AndInPeriod(df.format(fromDate), df.format(toDate),
				typeName);
		return sum;
	}

	public Double findSumForTypeLvl1(PlannedAggDTO agg) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date toDate = agg.getToDate();
		Date fromDate = agg.getFromDate();
		String typeName = agg.getType().getName();
		String subtypeOne = agg.getType().getSubtypeOne();
		Double sum = transRepo.findAllTransactionsSumByTypeLvl0AndTypeLvl1AndInPeriod(df.format(fromDate),
				df.format(toDate), typeName, subtypeOne);
		if (sum != null) {
			return sum;
		} else {
			return 0.0;
		}
	}

//	public Double findSumForTypeLvl2(PlannedAggDTO agg) {
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		Date toDate = agg.getToDate();
//		Date fromDate = agg.getFromDate();
//		String typeName = agg.getType().getName();
//		String subtypeOne = agg.getType().getSubtypeOne();
//		String subtypeTwo = agg.getType().getSubtypeTwo();
//		Double sum = transRepo.findAllTransactionsSumByTypeLvl0AndTypeLvl1AndTypeLvl2AndInPeriod(df.format(fromDate),
//				df.format(toDate), typeName, subtypeOne, subtypeTwo);
//		if (sum != null) {
//			return sum;
//		} else {
//			return 0.0;
//		}
//	}

	public List<TimeAggDTO> findIncomeTransactionsGroupedByYearAndMonth() {
		return transRepo.findAllIncomeTransactionSumsByYearAndMonth();
	}

	public List<TimeAggDTO> findExpensesTransactionsGroupedByYearAndMonth() {
		return transRepo.findAllExpensesTransactionSumsByYearAndMonth();
	}

	public List<TimeAggDTO> findAllIncomeTransactionsGroupedByYear() {
		return transRepo.findAllIncomeTransactionSumsByYear();
	}

	public List<TimeAggDTO> findAllExpensesTransactionsGroupedByYear() {
		return transRepo.findAllExpensesTransactionSumsByYear();
	}
}
