package hr.petkovic.incomeexpense.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.entity.FinancialTransaction;
import hr.petkovic.incomeexpense.entity.TransactionType;
import hr.petkovic.incomeexpense.repository.CompanyRepository;
import hr.petkovic.incomeexpense.repository.FinancialTransactionRepository;
import hr.petkovic.incomeexpense.repository.TransactionTypeRepository;

@Service
public class TransactionService {

	@Autowired
	private FinancialTransactionRepository transRepo;

	@Autowired
	private CompanyRepository companyRepo;

	@Autowired
	private TransactionTypeRepository typeRepo;

	public TransactionService(FinancialTransactionRepository transRepo, CompanyRepository companyRepo,
			TransactionTypeRepository typeRepo) {
		this.transRepo = transRepo;
		this.companyRepo = companyRepo;
		this.typeRepo = typeRepo;
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
			trans.setCurrency(transaction.getCurrency());
			trans.setDescription(transaction.getDescription());
			trans.setType(transaction.getType());
			return transRepo.save(trans);
		} else {
			return transRepo.save(transaction);
		}
	}
}
